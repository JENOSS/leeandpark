package board.board.controller;


import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


@RestController
public class SprintRestController {

    @RequestMapping(value = "/sprint_backlog/{sprintid}/save", method = RequestMethod.POST)

    public String postBacklog(@RequestBody Map<String,Object> param, @PathVariable Long sprintid) {

        System.out.println(param.get("result"));


        return "success";
    }
}