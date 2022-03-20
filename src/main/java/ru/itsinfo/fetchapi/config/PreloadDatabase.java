package ru.itsinfo.fetchapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itsinfo.fetchapi.model.Role;
import ru.itsinfo.fetchapi.model.User;
import ru.itsinfo.fetchapi.repository.RoleRepository;
import ru.itsinfo.fetchapi.repository.UserRepository;

import java.util.HashSet;

@Configuration
public class PreloadDatabase {

    private static final Logger log = LoggerFactory.getLogger(PreloadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository,
                                   UserRepository userRepository) {
        return args -> {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");

            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);

            userRepository.save(new User("root", "rootov", 49, "root@root.asd",
                    "root",
                    new HashSet<>() {{
                        add(roleAdmin);
                        add(roleUser);
                    }}));
            userRepository.save(new User("user", "userov", 46, "user@user.asd",
                    "user",
                    new HashSet<>() {{
                        add(roleUser);
                    }}));
            userRepository.save(new User("some", "one", 20, "some@one.user",
                    "guest",
                    new HashSet<>() {{
                        add(roleUser);
                    }}));
        };
    }
}
