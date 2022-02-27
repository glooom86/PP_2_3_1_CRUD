package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    private EntityManagerFactory entityManagerFactory;

    public UserDaoImpl(){}

    @Autowired
    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public List<User> index() {
        TypedQuery<User> query = entityManagerFactory
                .createEntityManager()
                .createQuery("from User", User.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public User show(int id) {
        TypedQuery<User> query = entityManagerFactory
                .createEntityManager()
                .createQuery("select u from User u WHERE u.id =:id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.persist(user);
        entityManager.flush();
    }

    @Transactional
    @Override
    public void update(int id, User updatedUser) {
        User targetUser = show(id);
        targetUser.setName(updatedUser.getName());
        targetUser.setLastName(updatedUser.getLastName());
        targetUser.setEmail(updatedUser.getEmail());
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.find(User.class, id);
        if(user != null) {
            entityManager.remove(user);
        }
    }
}
