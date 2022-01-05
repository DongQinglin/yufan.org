package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.Title;
import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorksToRepo extends JpaRepository<WorksToStyleWithStatus, Long> {

    Optional<WorksToStyleWithStatus> findOneByTitle_id(Long titleId);
}
