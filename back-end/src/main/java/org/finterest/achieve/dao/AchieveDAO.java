package org.finterest.achieve.dao;

import org.finterest.achieve.domain.AchieveVO;
import org.finterest.achieve.mapper.AchieveMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AchieveDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AchieveDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<AchieveVO> selectAllAchieve(){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAllAchieve();
    }

    public AchieveVO selectAchieveById(int id){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAchieveById(id);
    }

    public List<AchieveVO> selectAchieveByCategory(int categoryId){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).selectAchieveByCategory(categoryId);
    }


}
