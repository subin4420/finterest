package org.finterest.user.service;

import org.finterest.user.dao.AdminUserDAO;
import org.finterest.user.mapper.AdminUserMapper;
import org.finterest.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminUserService {
    private final AdminUserDAO adminUserDAO;

    @Autowired
    public AdminUserService(AdminUserDAO adminUserDAO) {
        this.adminUserDAO = adminUserDAO;
    }

    // 사용자 목록 조회
    public List<UserVO> getUsers() {
        return adminUserDAO.selectAllUsers();
    }

    // 3. 사용자 계정 상태 변경
    public void updateUserStatus(int userId, String accountStatus){
        adminUserDAO.updateUserStatus(userId, accountStatus);
    }

    public void deleteUser(int userId){
        adminUserDAO.deleteUser(userId);
    }

}
