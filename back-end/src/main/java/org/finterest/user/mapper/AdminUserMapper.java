package org.finterest.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.finterest.user.vo.UserVO;

import java.util.List;

@Mapper
public interface AdminUserMapper {

    // 사용자 목록 조회
    List<UserVO> selectAllUsers();

    // 사용자 계정 상태 변경
    void updateUserStatus(@Param("userId") int userId, @Param("accountStatus") String accountStatus);

    void deleteUser(@Param("userId")int userId);
}
