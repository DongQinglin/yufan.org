package me.dongqinglin.bootstarter.user.repository;

import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import me.dongqinglin.bootstarter.user.entity.UserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMetaRepo extends JpaRepository<UserMeta,Integer> {

    Optional<UserMeta> findByUser(FlinaUser user);

    Optional<UserMeta> findByUser_Id(Integer userId);
}
