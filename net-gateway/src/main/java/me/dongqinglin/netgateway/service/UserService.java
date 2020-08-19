package me.dongqinglin.netgateway.service;



import me.dongqinglin.netgateway.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public void save(User user);
    public void update(User user);
    public void deleteById(int id);
    public Optional<User> findById(int id);
    public Optional<User> findByUsername(String username);
    public List<User> findAll();
    //List<User> getLikeBy(User user);
}