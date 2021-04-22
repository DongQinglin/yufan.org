package me.dongqinglin.bootstarter.works.repository;

import me.dongqinglin.bootstarter.works.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepo extends JpaRepository<Score, Integer> {
}
