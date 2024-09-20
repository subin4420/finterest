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

    public AchieveVO one(int id){
        return achieveDAO.one(id);
    }

    public List<AchieveVO> all(){
        return achieveDAO.all();
    }

    public List<AchieveVO> categoryFilter(int category_id){
        return achieveDAO.categoryFilter(category_id);
    }
}
