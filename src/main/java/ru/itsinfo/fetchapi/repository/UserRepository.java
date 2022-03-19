package ru.itsinfo.fetchapi.repository;

import ru.itsinfo.fetchapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User entity);

    void deleteById(Long id);

    User findByName(String name);

    User update(User user);
}
