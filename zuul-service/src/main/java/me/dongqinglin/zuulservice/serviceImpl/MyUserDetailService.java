package me.dongqinglin.zuulservice.serviceImpl;


import me.dongqinglin.zuulservice.DAO.UserDao;
import me.dongqinglin.zuulservice.bean.MyUserDetail;
import me.dongqinglin.zuulservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException(("Not Found" + username)));
        return user.map(MyUserDetail::new).get();
    }
}