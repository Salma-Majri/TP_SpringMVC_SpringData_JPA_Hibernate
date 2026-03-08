package net.majri.springmvc.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.majri.springmvc.entities.AppUser;
import net.majri.springmvc.entities.Role;
import net.majri.springmvc.repository.AppUserRepository;
import net.majri.springmvc.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String confirmPassword) {
        if(!password.equals(confirmPassword)) throw new RuntimeException("Les mots de passe ne correspondent pas");

        String hashedPWD = passwordEncoder.encode(password);

        AppUser appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(hashedPWD)
                .build();

        return appUserRepository.save(appUser);
    }

    @Override
    public Role addNewRole(String roleName, String description) {
        Role role = roleRepository.findById(roleName).orElse(null);
        if(role != null) throw new RuntimeException("Ce rôle existe déjà");

        Role newRole = new Role(roleName, description);
        return roleRepository.save(newRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findById(roleName).orElse(null);

        if(appUser != null && role != null) {
            appUser.getRoles().add(role);
        }
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}