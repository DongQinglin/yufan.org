package me.dongqinglin.flina.rest.bussiness;


import me.dongqinglin.flina.rest.data.schema.entity.Status;
import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import me.dongqinglin.flina.rest.data.schema.repository.StatusRepo;
import me.dongqinglin.flina.rest.data.schema.repository.relation.VisitorToUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class VisitorService {

    @Autowired
    private VisitorToUserRepo toUserRepo;
    @Autowired
    private StatusRepo statusRepo;

    @Transactional
    public void disableVisitor(VisitorToUserWithStatus visitor) {
        visitor.setStatus(statusRepo.findOneByName(Status.StatusEnum.DISABLE.toString()));
        toUserRepo.save(visitor);
    }

    public void disableVisitor(Long id) {
        Optional<VisitorToUserWithStatus> oneByVisitor_id = toUserRepo.findOneByVisitor_id(id);
        if(oneByVisitor_id.isPresent()) {
            disableVisitor(oneByVisitor_id.get());
        }else {
            throw new IllegalArgumentException("未找到用户");
        }
    }

    public void disableVisitor(String emailOrName) {
        Optional<VisitorToUserWithStatus> oneByVisitor_email = toUserRepo.findOneByVisitor_email(emailOrName);
        if(oneByVisitor_email.isPresent()) {
            disableVisitor(oneByVisitor_email.get());
        }
        Optional<VisitorToUserWithStatus> oneByVisitor_name = toUserRepo.findOneByVisitor_name(emailOrName);
        if(oneByVisitor_name.isPresent()) {
            disableVisitor(oneByVisitor_name.get());
        }
        throw new IllegalArgumentException("未找到用户");
    }
}
