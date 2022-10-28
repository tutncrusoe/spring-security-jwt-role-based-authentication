package com.example.springsecurity.role;

import com.example.springsecurity.role.model.Role;
import com.example.springsecurity.role.repository.RoleRepository;
import com.example.springsecurity.user.model.User;
import com.example.springsecurity.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateRole() {
        Role admin = new Role("ROLE_ADMIN");
        Role editor = new Role("ROLE_USER");
        Role customer = new Role("ROLE_CUSTOMER");

        roleRepository.saveAll(List.of(admin, editor, customer));

        long count = roleRepository.count();
        assertEquals(3, count);
    }

    @Test
    public void testAssignRoleToUser() {

        String userEmail = "huy@email.com";
        String roleName = "ROLE_ADMIN";

        User user = userRepository.findByEmail(userEmail).get();
        System.out.println(user.getId());
        Role role = roleRepository.findByName(roleName).get();
        System.out.println(role.getId());
        user.addRole(role);

        User savedUser = userRepository.save(user);
        assertThat(savedUser.getRoles()).hasSize(1);
    }
}