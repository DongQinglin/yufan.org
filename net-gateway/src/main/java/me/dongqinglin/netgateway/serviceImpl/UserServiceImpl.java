package me.dongqinglin.netgateway.serviceImpl;


import me.dongqinglin.netgateway.DAO.UserDao;
import me.dongqinglin.netgateway.entity.User;
import me.dongqinglin.netgateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void save(User user) {
        userDao.save(user);
    }
    @Override
    public void update(User user) {
        userDao.save(user);
    }
    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }
    @Override
    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

}
