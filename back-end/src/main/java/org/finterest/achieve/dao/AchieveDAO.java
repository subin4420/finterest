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

    public AchieveVO one(int id){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).one(id);
    }

    public List<AchieveVO> all(){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).all();
    }

    public List<AchieveVO> categoryFilter(int category_id){
        return sqlSessionTemplate.getMapper(AchieveMapper.class).categoryFilter(category_id);
    }

}
