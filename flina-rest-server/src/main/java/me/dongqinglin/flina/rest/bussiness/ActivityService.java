package me.dongqinglin.flina.rest.bussiness;


import me.dongqinglin.flina.rest.data.schema.entity.Activity;
import me.dongqinglin.flina.rest.data.schema.entity.Style;
import me.dongqinglin.flina.rest.data.schema.repository.ActivityRepo;
import me.dongqinglin.flina.rest.data.schema.repository.StyleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepo activityRepo;
    @Autowired
    private StyleRepo styleRepo;


    public List<Activity> getEnableActivity() {
        return activityRepo.findAll();
    }

    public List<Style> getEnableStyle() {
        return styleRepo.findAll();
    }
}
