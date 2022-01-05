package me.dongqinglin.flina.rest.bussiness;

import me.dongqinglin.flina.rest.data.payload.response.Group;
import me.dongqinglin.flina.rest.data.schema.entity.Series;
import me.dongqinglin.flina.rest.data.schema.entity.relation.SeriesToVisitor;
import me.dongqinglin.flina.rest.data.schema.entity.relation.SeriesToWorks;
import me.dongqinglin.flina.rest.data.schema.repository.relation.SeriesToActivityRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.SeriesToVisitorRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.SeriesToWorksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GroupService {

    @Autowired
    private SeriesToWorksRepo seriesToWorksRepo;
    @Autowired
    private SeriesToVisitorRepo seriesToVisitorRepo;
    @Autowired
    private SeriesToActivityRepo seriesToActivityRepo;

    public Collection<Group> findAllByStatus_Name(String status) {

        List<SeriesToWorks> worksList = seriesToWorksRepo.findAllByStatus_name(status);
        List<SeriesToVisitor> visitorList = seriesToVisitorRepo.findAllByStatus_name(status);
        Map<Long, Group> seriesIdTogroupMap = new ConcurrentHashMap<>();
        worksList.stream().forEach(e -> {
            Series series = e.getSeries();
            if(!seriesIdTogroupMap.containsKey(series.getId())){
                seriesIdTogroupMap.put(series.getId(), new Group());
            }
            seriesIdTogroupMap.get(series.getId()).setSeries(series);
            seriesIdTogroupMap.get(series.getId()).getWorksList().add(e.getWorks());
        });
        visitorList.stream().forEach(e -> {
            Series series = e.getSeries();
            if(!seriesIdTogroupMap.containsKey(series.getId())){
                seriesIdTogroupMap.put(series.getId(), new Group());
            }
            seriesIdTogroupMap.get(series.getId()).getVisitorList().add(e.getVisitor());
        });
        return seriesIdTogroupMap.values();
    }
}
