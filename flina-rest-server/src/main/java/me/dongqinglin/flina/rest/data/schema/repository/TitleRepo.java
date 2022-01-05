package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.Score;
import me.dongqinglin.flina.rest.data.schema.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepo extends JpaRepository<Title, Long> {

}
