package board.board.controller;

import board.board.repository.UserRepository;
import board.board.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {


    @Autowired
    UserRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView welcome (ModelAndView mav){
        mav.setViewName("welcome");
        mav.addObject("msg","this is sample content");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login (ModelAndView mav){
        mav.setViewName("login");
        mav.addObject("msg","this is sample content");
        return mav;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("formModel") User user, ModelAndView mav){
        mav.setViewName("register");
        mav.addObject("msg","this is sample content");
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView regiseter_post(@ModelAttribute("formModel") User user, ModelAndView mav){
        repository.saveAndFlush(user);
        return new ModelAndView("redirect:/");
    }


}