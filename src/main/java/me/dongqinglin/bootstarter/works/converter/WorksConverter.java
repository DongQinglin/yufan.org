package me.dongqinglin.bootstarter.works.converter;

import me.dongqinglin.bootstarter.works.bean.WorksSaveRequest;
import me.dongqinglin.bootstarter.works.entity.Works;
import java.util.Date;

public class WorksConverter {

    public static Works fromReq(WorksSaveRequest request) {
        Works works = new Works();
        works.setTitle(request.getTitle());
        works.setStyle(request.getStyle());
        works.setContent(request.getContent());
        works.setEnable(true);
        works.setUpTime(new Date());
        System.out.println(request.getId());
        if(request.getId() != 0) works.setId(request.getId());
        return works;
    }
}
