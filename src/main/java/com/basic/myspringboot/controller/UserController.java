package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    //static/index.html => UserController의 leaf() 호출 => templates/leaf.html
    @GetMapping("/thymeleaf")
    public String leaf(Model model) {
        model.addAttribute("name","스프링부트");
        return "leaf";
    }

    /*
    public ModelAndView(String viewName,
                String modelName,
                Object modelObject)
     */
    @GetMapping("/index")
    public ModelAndView index() {
        List<User> userList = userRepository.findAll();
        return new ModelAndView("index","users",userList);
    }

//    @GetMapping("/index")
//    public String index(Model model) {
//        model.addAttribute("users", userRepository.findAll());
//        return "index";
//    }

}