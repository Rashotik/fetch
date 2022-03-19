package ru.itsinfo.fetchapi.repository;

import ru.itsinfo.fetchapi.model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    public Role findRoleByAuthority(String authority) throws NoSuchElementException {
        return findAll().stream()
                .filter(r -> authority.equals(r.getAuthority()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Role %s not found", authority)));
    }
    @Transactional
    public Role save(Role role){
        entityManager.persist(role);
        entityManager.merge(role);
        return role;
    }
    public Role getById(int id) {
        return entityManager.createQuery("select r from Role r WHERE r.id =:id", Role.class)
                .setParameter("id", id).getSingleResult();
    }
    public List<Role> getByName(String name) {
        return entityManager.createQuery("select r from Role r WHERE r.name =:name", Role.class)
                .setParameter("name", name).getResultList();
    }
}
