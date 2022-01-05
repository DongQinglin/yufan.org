package me.dongqinglin.flina.rest.index;

import me.dongqinglin.flina.rest.data.internal.IdPart;
import me.dongqinglin.flina.rest.data.schema.entity.*;
import me.dongqinglin.flina.rest.data.schema.entity.relation.*;
import me.dongqinglin.flina.rest.data.schema.repository.*;
import me.dongqinglin.flina.rest.data.schema.repository.relation.*;
import me.dongqinglin.flina.rest.middleware.jwt.JwtConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(JwtConfig.ANY_URL_PREFIX + "data")
public class DataController{

    Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired ProdUserMetaRepo prodUserMetaRepo;
    @Autowired ProdUserRepo prodUserRepo;
    @Autowired ProdWorksRepo prodWorksRepo;



    @Autowired StyleRepo styleRepo;
    @Autowired StatusRepo statusRepo;
    @Autowired StudentRepo studentRepo;
    @Autowired VisitorRepo visitorRepo;
    @Autowired UserRepo userRepo;
    @Autowired ActivityRepo activityRepo;
    @Autowired SeriesRepo seriesRepo;


    // works to style
    @Autowired TitleRepo titleRepo;
    @Autowired ContentRepo contentRepo;
    @Autowired WorksToRepo worksToRepo;
    @Autowired WorksToVisitorRepo worksToVisitorRepo;
    @Autowired VisitorToStudentRepo visitorToStudentRepo;
    @Autowired VisitorToUserRepo visitorToUserRepo;
    @Autowired ActivityToWorksRepo activityToWorksRepo;
    @Autowired SeriesToActivityRepo seriesToActivityRepo;
    @Autowired SeriesToWorksRepo seriesToWorksRepo;



    @GetMapping
//    @HasRole(value = User.RoleEnum.MANAGER)
    public void trans(){
        // get all works

        activityToWorksRepo.deleteAll();
        seriesToActivityRepo.deleteAll();
        seriesToWorksRepo.deleteAll();
        visitorToUserRepo.deleteAll();
        visitorToStudentRepo.deleteAll();
        worksToVisitorRepo.deleteAll();
        visitorRepo.deleteAll();
        studentRepo.deleteAll();
        worksToRepo.deleteAll();

        Series modern = seriesRepo.findById(1L).get();
        Series ancient = seriesRepo.findById(2L).get();
        Series article = seriesRepo.findById(3L).get();
        Series special = seriesRepo.findById(4L).get();
        logger.info("获取组: {},{},{},{}", modern.getName(), ancient.getName(), article.getName(), special.getName());
        Status enableStatus = statusRepo.findOneByName(Status.StatusEnum.ENABLE.toString());
        logger.info("获取正常状态: {}", enableStatus.getName());
        User visitorRole = userRepo.findOneByName(User.RoleEnum.VISITOR.toString());
        logger.info("获取访问者信息: {}", visitorRole.getName());
        Activity defaultActivity = activityRepo.findById(1L).get();
        logger.info("获取活动信息：{}", defaultActivity.getName());
        List<IdPart> allId = prodWorksRepo.findAllId();
        int size = allId.size();
        for (int i = 0; i < size; i++) {
            ProdWorks prodWorks = prodWorksRepo.getOne(allId.get(i).getId());
            logger.info("操作{}/{}篇作品，作品名{}",i + 1, size, prodWorks.getTitle());

            // set naw works
            Title saveTitle = titleRepo.save(new Title(prodWorks.getTitle()));
            Content saveContent = contentRepo.save(new Content(prodWorks.getContent()));

            WorksToStyleWithStatus worksToStyleWithStatus = new WorksToStyleWithStatus();
            worksToStyleWithStatus.setStatus(statusRepo.findOneByName(Status.StatusEnum.ENABLE.toString()));
            worksToStyleWithStatus.setTitle(saveTitle);
            worksToStyleWithStatus.setContent(saveContent);
            Style saveStyle = styleRepo.findOneByName(prodWorks.getStyle());
            logger.info("获取体裁：{}", saveStyle.getName());
            worksToStyleWithStatus.setStyle(saveStyle);
            WorksToStyleWithStatus saveWorks = worksToRepo.save(worksToStyleWithStatus);
            logger.info("保存作品：{}", saveWorks.getTitle().getTitle());
            // set works to visitor

            VisitorToStudentWithStatus visitorToStudentWithStatus = new VisitorToStudentWithStatus();
            visitorToStudentWithStatus.setStatus(enableStatus);
            // set student
            List<PropUserMeta> byStudentId = prodUserMetaRepo.findByStudentId(prodWorks.getAuthor_stu_id().toString());
            if(byStudentId.isEmpty()) {
                worksToRepo.delete(saveWorks);
                titleRepo.delete(saveTitle);
                contentRepo.delete(saveContent);
                continue;
            };

            PropUserMeta metaByStudentId = byStudentId.get(0);
            logger.info("获取作者信息：{},学号：{}",metaByStudentId.getName(), metaByStudentId.getStudentId());
            Student student = new Student();
            student.setStudentId(metaByStudentId.getStudentId());
            student.setCollege(metaByStudentId.getCollege());
            student.setName(metaByStudentId.getName());
            student.setContact(metaByStudentId.getConcat());
            Student saveStudent;
            Optional<Student> oneByStudnetId = studentRepo.findOneByStudentId(metaByStudentId.getStudentId());
            if(oneByStudnetId.isPresent()) {
                saveStudent = oneByStudnetId.get();
            }else  {
                saveStudent = studentRepo.save(student);
            }

            logger.info("保存作者信息{}", saveStudent.getName());
            visitorToStudentWithStatus.setStudent(saveStudent);
            // set visitor
            ProdUser userByStudentId = prodUserRepo.findOneById(metaByStudentId.getAuthor_id());
            logger.info("获取登陆信息：{}，邮箱{}", userByStudentId.getName(), userByStudentId.getEmail());
            Visitor visitor = new Visitor();
            visitor.setName(userByStudentId.getName());
            visitor.setPass(userByStudentId.getPass());
            visitor.setEmail(userByStudentId.getEmail());
            Optional<Visitor> oneByEmail = visitorRepo.findOneByEmail(userByStudentId.getEmail());
            Visitor saveVisitor;
            if(oneByEmail.isPresent()) {
                saveVisitor = oneByEmail.get();
            }else {
                saveVisitor = visitorRepo.save(visitor);

                logger.info("保存登陆信息{}", visitor.getName());
                // set visitor to student
                visitorToStudentWithStatus.setVisitor(saveVisitor);
                visitorToStudentWithStatus.setStatus(enableStatus);
                visitorToStudentWithStatus.setStudent(saveStudent);
                visitorToStudentRepo.save(visitorToStudentWithStatus);
                logger.info("保存作者-登陆信息:{} - {}", saveStudent.getName(), saveVisitor.getName());
                // set visitor to user

                VisitorToUserWithStatus visitorToUserWithStatus = new VisitorToUserWithStatus();
                visitorToUserWithStatus.setUser(visitorRole);
                visitorToUserWithStatus.setVisitor(saveVisitor);
                visitorToUserWithStatus.setStatus(enableStatus);
                visitorToUserRepo.save(visitorToUserWithStatus);
                logger.info("保存权限-登陆信息: {} - {}", visitorRole.getName(), saveStudent.getName());
                // set works to visitor
            }
            WorksToVisitorWithStatus worksToVisitorWithStatus = new WorksToVisitorWithStatus();
            worksToVisitorWithStatus.setVisitor(saveVisitor);
            worksToVisitorWithStatus.setWorks(saveWorks);
            worksToVisitorWithStatus.setStatus(enableStatus);
            worksToVisitorRepo.save(worksToVisitorWithStatus);
            logger.info("保存作者-作品信息: {} - {}", saveStudent.getName(), saveWorks.getTitle());

            // set works to activity

            ActivityToWorks activityToWorks = new ActivityToWorks();
            activityToWorks.setActivity(defaultActivity);
            activityToWorks.setWorks(saveWorks);
            activityToWorks.setStatus(enableStatus);
            activityToWorksRepo.save(activityToWorks);
            logger.info("保存活动-作品信息: {} - {}", defaultActivity.getName(), saveWorks.getTitle());

            SeriesToActivity seriesToActivity = new SeriesToActivity();
            SeriesToWorks seriesToWorks = new SeriesToWorks();
            Series series = null;
            switch (saveStyle.getName()) {
                case "ancient":
                    series = ancient;
                    break;
                case "modern":
                    series = modern;
                    break;
                case "article":
                    series = article;
                    break;
                case "special":
                    series = special;
                    break;
                default:
                    throw new IllegalArgumentException("不存在作品类别");
            }
            logger.info("获取组信息：{}", series.getName());
            seriesToActivity.setSeries(series);
            seriesToActivity.setStatus(enableStatus);
            seriesToActivity.setActivity(defaultActivity);
            seriesToActivityRepo.save(seriesToActivity);
            logger.info("保存活动-组信息: {} - {}", defaultActivity.getName(), series.getName());

            seriesToWorks.setWorks(saveWorks);
            seriesToWorks.setStatus(enableStatus);
            seriesToWorks.setSeries(series);
            seriesToWorksRepo.save(seriesToWorks);
            logger.info("保存作品-组信息: {} - {}", saveWorks.getTitle(), series.getName());

        }

    }


}
