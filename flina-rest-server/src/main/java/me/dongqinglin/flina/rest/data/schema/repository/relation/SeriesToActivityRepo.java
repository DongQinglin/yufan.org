
package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.relation.SeriesToActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesToActivityRepo extends JpaRepository<SeriesToActivity, Long> {

    List<SeriesToActivity> findAllByStatus_name(String status);

}