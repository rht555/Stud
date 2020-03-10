package com.rht.shpringbootwork.controller;

import com.rht.shpringbootwork.mapper.QuesstionMapper;
import com.rht.shpringbootwork.mapper.UserMapper;
import com.rht.shpringbootwork.model.Question;
import com.rht.shpringbootwork.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {
    @Autowired(required = false)
    private QuesstionMapper quesstionMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String value = cookie.getValue();
                    user = userMapper.findByToken(value);
                    if (user!=null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
            if (user == null){
                model.addAttribute("error","用户未登录");
                return "publish";
            }
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        quesstionMapper.create(question);
        return "redirect:/index ";
    }
}
