package web.services;

import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User show(int id);
    void save(User user);
    void delete(int id);
    void update(int id,User user);
}
