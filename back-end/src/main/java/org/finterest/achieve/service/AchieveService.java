package org.finterest.achieve.service;

import org.finterest.achieve.dao.AchieveDAO;
import org.finterest.achieve.domain.AchieveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchieveService {
    private final AchieveDAO achieveDAO;

    @Autowired
    public AchieveService(AchieveDAO achieveDAO) {
        this.achieveDAO = achieveDAO;
    }

    public List<AchieveVO> selectAllAchieve(){
        return achieveDAO.selectAllAchieve();
    }

    public AchieveVO selectAchieveById(int id){
        return achieveDAO.selectAchieveById(id);
    }

    public List<AchieveVO> selectAchieveByCategory(int categoryId){
        return achieveDAO.selectAchieveByCategory(categoryId);
    }

}
