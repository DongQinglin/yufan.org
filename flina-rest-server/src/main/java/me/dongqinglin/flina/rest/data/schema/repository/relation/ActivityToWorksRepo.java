package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.relation.ActivityToWorks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityToWorksRepo extends JpaRepository<ActivityToWorks, Long> {
}