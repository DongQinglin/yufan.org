package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.internal.IdPart;
import me.dongqinglin.flina.rest.data.schema.entity.ProdWorks;
import me.dongqinglin.flina.rest.data.schema.entity.PropUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdWorksRepo extends JpaRepository<ProdWorks, Long> {

    @Query(nativeQuery = true, value = "select id from prodworks")
    List<IdPart> findAllId();

}
