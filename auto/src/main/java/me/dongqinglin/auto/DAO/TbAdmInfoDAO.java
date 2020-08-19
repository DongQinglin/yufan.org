package me.dongqinglin.auto.DAO;
import me.dongqinglin.auto.entity.TbAdmInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TbAdmInfoDAO extends JpaRepository<TbAdmInfo,Integer> {
    //filter在前端得以实现，后端就不再需要模糊查询
    //@Query(value = "select * from tb_adm_info as t where t.列名 like %?1%",nativeQuery = true)
    //List<UsersModel> findLikeBy列名大驼峰(String 列名小驼峰);
}