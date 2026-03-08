package net.majri.springmvc.service;


import net.majri.springmvc.entities.AppUser;
import net.majri.springmvc.entities.Role;

public interface AccountService {
    AppUser addNewUser(String username, String password, String confirmPassword);

    Role addNewRole(String roleName, String description);

    void addRoleToUser(String username, String roleName);

    AppUser loadUserByUsername(String username);
}