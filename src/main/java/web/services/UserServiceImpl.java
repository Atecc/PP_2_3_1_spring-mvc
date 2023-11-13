package web.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Transactional(readOnly = true)
    @Override
    public User show(int id) {
        return userDao.show(id);
    }
    @Transactional
    @Override
    public void save(User user) {
        userDao.save(user);
    }
    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
    @Transactional
    @Override
    public void update(int id, User user) {
        userDao.update(id, user);
    }
}
