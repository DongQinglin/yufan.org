package me.dongqinglin.bootstarter.Secure.repository;

import me.dongqinglin.bootstarter.Secure.entity.FlinaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlinaUserRepo extends JpaRepository<FlinaUser, Integer> {
    Optional<FlinaUser> findByUsername(String username);
}
