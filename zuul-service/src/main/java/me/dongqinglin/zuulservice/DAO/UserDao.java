package me.dongqinglin.zuulservice.DAO;

import me.dongqinglin.zuulservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    //@Query(value = "select * from user as t where t.列名 like %?1%",nativeQuery = true)
    //List<UsersModel> findLikeBy列名大驼峰(String 列名小驼峰);

    Optional<User> findByUsername(String username);
}