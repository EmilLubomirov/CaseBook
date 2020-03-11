package app.repository.impl;

import app.domain.entities.User;
import app.repository.UserRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


public class UserRepositoryImpl implements UserRepository
{
    private final EntityManager entityManager;

    @Inject
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void registerUser(User user)
    {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

    }

    @Override
    public User findByUAndP(String username, String password)
    {

        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
    }

    @Override
    public List<User> getAllUsers()
    {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Override
    public User findById(String id)
    {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
           .setParameter("id", id).getSingleResult();
    }

    @Override
    public void update(User user)
    {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }
}
