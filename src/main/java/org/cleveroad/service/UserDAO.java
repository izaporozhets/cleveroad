package org.cleveroad.service;

import org.cleveroad.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> findAll();
    void save();
    void update();
    void delete();
    int count();
}
