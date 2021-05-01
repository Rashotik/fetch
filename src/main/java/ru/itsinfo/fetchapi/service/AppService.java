package ru.itsinfo.fetchapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import ru.itsinfo.fetchapi.model.Role;
import ru.itsinfo.fetchapi.model.User;

import java.util.List;

public interface AppService extends UserDetailsService {

    List<User> findAllUsers();

    User getOneUser(Long id);

    User insertUser(User user, BindingResult bindingResult);

    User updateUser(User user, BindingResult bindingResult);

    void deleteUser(Long id);

    Iterable<Role> findAllRoles();
}
