package cz.fi.muni.pa165.soccermanager.dao;

import cz.fi.muni.pa165.soccermanager.data.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of a DAO layer for a {@link User} entity.
 *
 * @author Lenka Horvathova
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUserName(String userName) {
        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.userName = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public boolean isTeamAlreadyAssignedToUser(Long teamId) {
        try {
            entityManager
            	.createQuery("SELECT u FROM User u INNER JOIN u.team t WHERE t.id = :teamId", User.class)
            	.setParameter("teamId", teamId)
            	.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }

    }

    @Override
    public Long getNumberOfAdministrators() {
        return (Long) entityManager.createQuery("SELECT count(*) FROM User u WHERE u.admin = true").getSingleResult();
    }
}
