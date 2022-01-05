package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepo extends JpaRepository<Series, Long> {

    Series findOneByName(String name);


}
