package me.dongqinglin.bootstarter.works.repository;

import me.dongqinglin.bootstarter.works.entity.Works;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface WorksRepo extends JpaRepository<Works, Integer> {

    List<Works> findByUser_Id(Integer userId);
}
