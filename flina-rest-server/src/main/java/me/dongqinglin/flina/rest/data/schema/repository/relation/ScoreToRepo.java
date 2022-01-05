package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.relation.SeriesToVisitor;
import me.dongqinglin.flina.rest.data.schema.entity.relation.WorksToScoreToVisitorToSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreToRepo extends JpaRepository<WorksToScoreToVisitorToSeries, Long> {
    List<WorksToScoreToVisitorToSeries> findAllByWorks_Id(Long id);
}
