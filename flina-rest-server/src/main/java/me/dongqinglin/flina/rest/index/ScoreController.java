package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.bussiness.ActivityService;
import me.dongqinglin.flina.rest.bussiness.WorksGroupService;
import me.dongqinglin.flina.rest.bussiness.WorksWorksGroupServzice;
import me.dongqinglin.flina.rest.data.payload.response.Message;
import me.dongqinglin.flina.rest.data.schema.entity.*;
import me.dongqinglin.flina.rest.data.schema.entity.relation.WorksToScoreToVisitorToSeries;
import me.dongqinglin.flina.rest.data.schema.repository.*;
import me.dongqinglin.flina.rest.data.schema.repository.relation.ScoreToRepo;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import me.dongqinglin.flina.rest.middleware.role.HasRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(JwtConfig.AUTH_URL_PREFIX + "score")
public class ScoreController {


    @Autowired
    StyleRepo styleRepo;
    @Autowired
    StatusRepo statusRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    VisitorRepo visitorRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired ActivityRepo activityRepo;
    @Autowired SeriesRepo seriesRepo;

    @Autowired ScoreRepo scoreRepo;
    @Autowired
    ScoreToRepo scoreToRepo;
    @Autowired WorksToRepo worksToRepo;

    @GetMapping("/works")
    public Message getScoreByWorksId(Long id) {
        Message message = Message.createSuccessMessage("查找成功");
        List<WorksToScoreToVisitorToSeries> allByWorksToStyleWithStatus_id = scoreToRepo.findAllByWorks_Id(id);

        return message.setData(allByWorksToStyleWithStatus_id);
    }


    @PostMapping("/works")
    public Message getScoreByWorksId(@RequestBody  WorksToScoreToVisitorToSeries worksToScoreToVisitorToSeries) {
        Message message = Message.createSuccessMessage("保存成功");
        Status status = statusRepo.findOneByName(Status.StatusEnum.ENABLE.toString());
        Visitor visitor = visitorRepo.findById(worksToScoreToVisitorToSeries.getVisitor().getId()).get();
        WorksToStyleWithStatus worksToStyleWithStatus = worksToRepo.findById(worksToScoreToVisitorToSeries.getWorks().getId()).get();
        Score score = scoreRepo.save(worksToScoreToVisitorToSeries.getScore());
        Series series = seriesRepo.findById(worksToScoreToVisitorToSeries.getSeries().getId()).get();
        worksToScoreToVisitorToSeries.setStatus(status);
        worksToScoreToVisitorToSeries.setWorks(worksToStyleWithStatus);
        worksToScoreToVisitorToSeries.setVisitor(visitor);
        worksToScoreToVisitorToSeries.setSeries(series);
        scoreToRepo.save(worksToScoreToVisitorToSeries);
        return message.setData(worksToScoreToVisitorToSeries);
    }




}
