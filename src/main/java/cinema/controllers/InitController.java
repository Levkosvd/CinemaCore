package cinema.controllers;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.RoleService;
import cinema.service.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class InitController implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public InitController(UserService userService,
                          RoleService roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        Role userRole = new Role();
        userRole.setName("USER");
        roleService.add(adminRole);
        roleService.add(userRole);
        Role admin = roleService.findByRoleName("ADMIN");
        User user1 = new User();

        user1.setPassword(passwordEncoder.encode("1111"));
        user1.setEmail("admin@gmail.com");
        user1.getRoles().add(admin);
        userService.add(user1);

        alreadySetup = true;
    }
}
