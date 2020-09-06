package me.dongqinglin.auto.serviceImplement;
import java.util.List;
import java.util.Optional;

import me.dongqinglin.auto.DAO.TbAdmInfoDAO;
import me.dongqinglin.auto.entity.TbAdmInfo;
import me.dongqinglin.auto.service.TbAdmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbAdmInfoServiceImpl implements TbAdmInfoService {
    @Autowired
    private TbAdmInfoDAO tbAdmInfoDao;
    public void save(TbAdmInfo tbAdmInfo){tbAdmInfoDao.save(tbAdmInfo);}
    public void update(TbAdmInfo tbAdmInfo){tbAdmInfoDao.save(tbAdmInfo);}
    public void deleteById(int id){tbAdmInfoDao.deleteById(id);}
    public List<TbAdmInfo> findAll(){return tbAdmInfoDao.findAll();}
    public Optional<TbAdmInfo> findById(int id){return tbAdmInfoDao.findById(id);}
    //filter在前端得以实现，后端就不再需要模糊查询
    //List<TbAdmInfo> findLikeBy(TbAdmInfo tbAdmInfo){return tbAdmInfoDao.findLikeBy列名大驼峰(tbAdmInfo.getTbAdmInfoby列名大驼峰());}
}