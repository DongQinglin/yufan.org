package me.dongqinglin.bootstarter.works.repository;

import me.dongqinglin.bootstarter.works.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Integer> {
}
