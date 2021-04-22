package me.dongqinglin.bootstarter.works.service;


import me.dongqinglin.bootstarter.works.entity.Activity;
import me.dongqinglin.bootstarter.works.repository.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepo repo;


    public List<Activity> getEnableActivity() {
        return repo.findAll();
    }
}
