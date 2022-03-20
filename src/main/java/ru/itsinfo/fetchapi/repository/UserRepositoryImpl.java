package ru.itsinfo.fetchapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itsinfo.fetchapi.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email = :email", User.class);
        query.setParameter("email", email);
        return Optional.ofNullable(query.getSingleResult());
    }
    @Override
    public User findByName(String name) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.firstName = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Transactional
    @Override
    public User save(User entity) {
        entity.setPassword(entity.getPassword());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        try {
            entityManager.persist(entity);
            entityManager.flush();
        }catch (Exception e){}
        return entity;
    }
    @Transactional
    public User update(User updatedUser) {
        entityManager.merge(updatedUser);
        return updatedUser;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        User entity = findById(id).get();
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
