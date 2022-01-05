package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.Activity;
import me.dongqinglin.flina.rest.data.schema.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StyleRepo extends JpaRepository<Style, Long> {

    Style findOneByName(String name);
}
