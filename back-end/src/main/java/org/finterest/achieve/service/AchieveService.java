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

//    public String insert(BoardVO boardVO){
//        String result = "게시판 글삽입 실패";
//        if(boardDAO.insert(boardVO) == 1){
//            result = "게시판 글삽입 성공";
//        };
//        return result;
//    }
//
//    public String update(BoardVO boardVO){
//        String result = "게시판 글수정 실패";
//        if(boardDAO.update(boardVO) != 0){
//            result = "게시판 글수정 성공";
//        };
//        return result;
//    }
//
//    public String delete(int no){
//        String result = "게시판 글삭제 실패";
//        if(boardDAO.delete(no) != 0){
//            result = "게시판 글삭제 성공";
//        };
//        return result;
//    }
//
    public AchieveVO one(int id){return achieveDAO.one(id);
    }

    public List<AchieveVO> all(){
        return achieveDAO.all();
    }

    public List<AchieveVO> categoryFilter(int category_id){return achieveDAO.categoryFilter(category_id);}
}