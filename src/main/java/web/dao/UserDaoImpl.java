package web.dao;
import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUser() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public void delete(int id) {
        User user = show(id);
        if (null == user) {
            throw new NullPointerException("User not found");
        }
        entityManager.remove(user);
        entityManager.flush();
    }

    @Override
    public void update(int id, User user) {
        entityManager.merge(user);
        entityManager.flush();
    }
}
