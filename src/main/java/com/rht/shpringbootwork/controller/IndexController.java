package com.rht.shpringbootwork.controller;

import com.rht.shpringbootwork.dto.PaginationDTO;
import com.rht.shpringbootwork.dto.QuestionDTO;
import com.rht.shpringbootwork.mapper.QuesstionMapper;
import com.rht.shpringbootwork.mapper.UserMapper;
import com.rht.shpringbootwork.model.User;
import com.rht.shpringbootwork.service.QuestionServicee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionServicee questionServicee;

    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size ){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String value = cookie.getValue();
                    User user = userMapper.findByToken(value);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionServicee.list(page,size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
