package me.dongqinglin.bootstarter.Secure.service;

import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.Secure.repository.FlinaUserRepo;
import me.dongqinglin.bootstarter.Secure.bean.FlinaUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlinaUserDetailService implements UserDetailsService {

    @Autowired
    FlinaUserRepo UserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<FlinaUser> user = UserRepo.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException(("Not Found" + username)));
        return user.map(FlinaUserDetail::new).get();
    }

    public UserDetails loadUserByUsername(FlinaUser user) throws UsernameNotFoundException {
        return new FlinaUserDetail(user);
    }

}

