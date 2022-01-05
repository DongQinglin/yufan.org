
package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.relation.SeriesToWorks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesToWorksRepo extends JpaRepository<SeriesToWorks, Long> {
    List<SeriesToWorks> findAllByStatus_name(String status);
}