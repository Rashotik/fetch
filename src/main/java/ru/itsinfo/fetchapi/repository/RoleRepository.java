package ru.itsinfo.fetchapi.repository;

import ru.itsinfo.fetchapi.model.Role;

import java.util.List;
import java.util.NoSuchElementException;

public interface RoleRepository {
    List<Role> findAll();

    Role findRoleByAuthority(String authority) throws NoSuchElementException;

    Role save(Role role);

    Role getById(Long id);

    List<Role> getByName(String name);
}
