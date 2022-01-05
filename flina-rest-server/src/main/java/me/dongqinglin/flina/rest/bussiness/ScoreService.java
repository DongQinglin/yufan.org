package me.dongqinglin.flina.rest.bussiness;


import me.dongqinglin.flina.rest.data.payload.request.ScoreSaveRequest;
import me.dongqinglin.flina.rest.data.schema.entity.Score;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.schema.entity.relation.WorksToScoreToVisitorToSeries;
import me.dongqinglin.flina.rest.data.schema.entity.WorksToStyleWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.ScoreRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.ScoreToRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ScoreService {
    @Autowired
    private ScoreRepo scoreRepo;

    @Autowired
    private ScoreToRepo scoreToRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private WorksService worksService;

    public List<Score> listScore(Integer worksId) {
//        return this.repo.findAllByWorks(worksId);;
        return null;
    }


    public void save(ScoreSaveRequest score) {
        Score score1 = new Score();
        score1.setEditorReason(score.getEditedReason());
        score1.setEditorScore(score.getScore());
        Score save = scoreRepo.save(score1);
        VisitorToUserWithStatus user = authService.getUser(score.getEditorName());
        WorksToStyleWithStatus byId = worksService.findById(score.getWorksId());
        if(Objects.isNull(byId)) {
            throw new IllegalArgumentException("找不到作品");
        }
        WorksToScoreToVisitorToSeries worksToScoreToVisitor = new WorksToScoreToVisitorToSeries();
        worksToScoreToVisitor.setScore(save);
        worksToScoreToVisitor.setVisitor(user.getVisitor());
        worksToScoreToVisitor.setWorks(byId);


        scoreToRepo.save(worksToScoreToVisitor);


    }
}
