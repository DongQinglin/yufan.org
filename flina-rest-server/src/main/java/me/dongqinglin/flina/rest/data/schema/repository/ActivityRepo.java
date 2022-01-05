package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {
}
