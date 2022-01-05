
package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.relation.SeriesToVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesToVisitorRepo extends JpaRepository<SeriesToVisitor, Long> {
    List<SeriesToVisitor> findAllByStatus_name(String status);
}