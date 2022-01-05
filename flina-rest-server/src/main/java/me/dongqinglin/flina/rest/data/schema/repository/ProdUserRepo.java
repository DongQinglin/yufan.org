package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.ProdUser;
import me.dongqinglin.flina.rest.data.schema.entity.Score;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdUserRepo extends JpaRepository<ProdUser, Long> {
    ProdUser findOneByName(String name);
    ProdUser findOneById(Long id);
}
