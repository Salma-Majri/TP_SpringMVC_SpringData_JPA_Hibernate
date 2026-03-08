package net.majri.springmvc.repository;

import net.majri.springmvc.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    //
}