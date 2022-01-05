package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.ProdUser;
import me.dongqinglin.flina.rest.data.schema.entity.PropUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdUserMetaRepo extends JpaRepository<PropUserMeta, Long> {
    List<PropUserMeta> findByStudentId(String studentId);
}
