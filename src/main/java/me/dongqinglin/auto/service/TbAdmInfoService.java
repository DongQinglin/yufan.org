package me.dongqinglin.auto.service;
import me.dongqinglin.auto.entity.TbAdmInfo;

import java.util.List;
import java.util.Optional;

public interface TbAdmInfoService {
    public void save(TbAdmInfo tbAdmInfo);
    public void deleteById(int id);
    public List<TbAdmInfo> findAll();
    public void update(TbAdmInfo tbAdmInfo);
    public Optional<TbAdmInfo> findById(int id);
    //filter在前端得以实现，后端就不再需要模糊查询
    //List<TbAdmInfo> getLikeBy(TbAdmInfo tbAdmInfo);
}