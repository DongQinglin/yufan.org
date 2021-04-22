package me.dongqinglin.bootstarter.works.repository;

import me.dongqinglin.bootstarter.works.entity.Activity;
import me.dongqinglin.bootstarter.works.entity.WorksGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface WorksGroupRepo extends JpaRepository<WorksGroup, Integer> {
    Set<WorksGroup> findByActivity_Id(Integer activityId);
    Set<WorksGroup> findByActivity(Activity activity);
}
