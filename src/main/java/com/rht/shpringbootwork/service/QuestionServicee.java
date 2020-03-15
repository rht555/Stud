package com.rht.shpringbootwork.service;

import com.rht.shpringbootwork.dto.PaginationDTO;
import com.rht.shpringbootwork.dto.QuestionDTO;
import com.rht.shpringbootwork.mapper.QuesstionMapper;
import com.rht.shpringbootwork.mapper.UserMapper;
import com.rht.shpringbootwork.model.Question;
import com.rht.shpringbootwork.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServicee {

    @Autowired(required = false)
    private QuesstionMapper quesstionMapper;

    @Autowired(required = false)
    private UserMapper userMapper;


    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = quesstionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if (page<1){
            page = 1;
        }
        if (page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page - 1);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> question = quesstionMapper.list(offset,size);

        for (Question question1 : question) {
            User user = userMapper.findById(question1.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question1, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
