package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.*;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    public UserDaoImpl(){}


    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public List<User> index() {
        TypedQuery<User> query = em
                .createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User show(Long id) {
        TypedQuery<User> query = em
                .createQuery("select u from User u WHERE u.id =:id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void update(User updatedUser) {
        User targetUser = show(updatedUser.getId());
        targetUser.setName(updatedUser.getName());
        targetUser.setLastName(updatedUser.getLastName());
        targetUser.setEmail(updatedUser.getEmail());
        em.merge(targetUser);
    }

    @Override
    public void deleteById(Long id) {
        User user = em.find(User.class, id);
        if(user != null) {
            em.remove(user);
        }
    }
}
