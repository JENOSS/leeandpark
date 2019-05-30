package board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class MyController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome (){

        return "welcome";
    }

    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String mainproject() {

        return "index";
    }

    @RequestMapping(value="/sprint", method=RequestMethod.GET)
    public String sprint() {

        return "sprint";
    }

    @RequestMapping(value="/pmem", method=RequestMethod.GET)
    public String pmem() {

        return "findProjectMember";
    }
    @RequestMapping(value = "/pmem", method = RequestMethod.PUT)
    @ResponseBody
    public String helloWorld()  {
        return "search";
    }



}