package org.finterest.user.dao;

import org.apache.ibatis.annotations.Param;
import org.finterest.user.mapper.AdminUserMapper;
import org.finterest.user.vo.UserVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminUserDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public AdminUserDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    // 1. 사용자 목록 조회
    public List<UserVO> selectAllUsers(){
        return sqlSessionTemplate.getMapper(AdminUserMapper.class).selectAllUsers();
    }

    // 3. 사용자 계정 상태 변경
    public void updateUserStatus(int userId, String accountStatus){
        sqlSessionTemplate.getMapper(AdminUserMapper.class).updateUserStatus(userId, accountStatus);
    }

    public void deleteUser(int userId){
        sqlSessionTemplate.getMapper(AdminUserMapper.class).deleteUser(userId);
    }

}
