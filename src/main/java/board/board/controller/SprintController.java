package board.board.controller;

import board.board.model.Sprint;
import board.board.model.SprintBacklog;
import board.board.model.SprintTodo;
import board.board.repository.SprintBacklogRepository;
import board.board.repository.SprintRepository;
import board.board.repository.SprintTodoRepository;
import board.board.service.ProjectService;
import board.board.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class SprintController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private SprintBacklogRepository sprintBacklogRepository;

    @Autowired
    private SprintTodoRepository sprinttodoRepository;

    @RequestMapping(value="/sprint/{projectidx}", method= RequestMethod.GET)
    public String sprint(@PathVariable int projectidx) throws Exception{

        ModelAndView mv = new ModelAndView();

        Sprint sprint = sprintRepository.findByProjectidx(projectidx);

        int level = sprint.getLevel();

        Long sprintid = sprint.getSprintid();

        switch (level){

            case 0:
                return "redirect:/sprint_start/"+sprintid;
            case 1:
                return "redirect:/sprint_backlog/"+sprintid;
            case 2:
                return "redirect:/sprint_plan/"+sprintid;
            case 3:
                return "redirect:/scrumboard/"+sprintid;
            case 4:
                return "redirect:/sprint_re/"+sprintid;
        }

        return "no";
    }

    @RequestMapping(value="/sprint_start/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprintstart_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_start");
        mv.addObject("sprintid",sprintid);

        return mv;
    }


    @RequestMapping(value="/sprint_backlog/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprintbacklog_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_backlog");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);

        if (sp.getLevel() < 1)
            sprintRepository.updateLevelBySprintid(sprintid,1);


        return mv;
    }

/*
    @RequestMapping(value="/sprint_backlog/{sprintid}", method= RequestMethod.POST)
    public ModelAndView sprintbacklog_post(SprintBacklog sprintbacklog, @PathVariable Long sprintid) throws Exception{

        sprintbacklog.setSprintid(sprintid);
        sprintbacklog.setIsdoing("no");

        sprintBacklogRepository.save(sprintbacklog);

        ModelAndView mv = new ModelAndView("redirect:/sprint_backlog/{sprintid}");
        mv.addObject("sprintid",sprintid);

        return mv;

    }
*/

    @RequestMapping(value = "/sprint_backlog/{sprintid}", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addbacklog(@ModelAttribute SprintBacklog sprintBacklog, @PathVariable Long sprintid) {

        ModelAndView mv = new ModelAndView("redirect:/sprint_backlog/{sprintid}");

        sprintBacklog.setSprintid(sprintid);
        sprintBacklog.setIsdoing("no");

        sprintBacklogRepository.save(sprintBacklog);
        sprintBacklogRepository.findBySprintid(sprintid) ;

        List<SprintBacklog> list = sprintBacklogRepository.findBySprintid(sprintid);
        mv.addObject("list", list);

        System.out.println(list);
        return mv;


    }


    @RequestMapping(value="/sprint_plan/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprinplan_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_plan");
        mv.addObject("sprintid",sprintid);


        Sprint sp = sprintRepository.findBySprintid(sprintid);

        if (sp.getLevel() < 2)
            sprintRepository.updateLevelBySprintid(sprintid,2);


        return mv;
    }

    @RequestMapping(value="/sprint_plan/{sprintid}", method= RequestMethod.POST)
    public ModelAndView sprintplan_post(SprintTodo sprinttodo, @PathVariable Long sprintid) throws Exception{

        sprinttodo.setSprintid(sprintid);
        sprinttodo.setIsdoing("n");
        sprinttodo.setIsdone("n");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        sprinttodo.setUsername(username);

        sprinttodoRepository.save(sprinttodo);

        // sprintid에 해당하는 sprint 의 cycle 여기서 설정
        sprintRepository.updateCycleBySprintid(sprintid, sprinttodo.getCycle());


        ModelAndView mv = new ModelAndView("redirect:/scrumboard/{sprintid}");
        mv.addObject("sprintid",sprintid);


        return mv;

    }



    @RequestMapping(value="/scrumboard/{sprintid}", method= RequestMethod.GET)
    public ModelAndView scrumboard_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("scrumboard");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);
        int cycle = sp.getCycle();
        //경과일 = 스프린트 생성날짜- 현재 날짜
        int pass = sprintService.dateCheck(sp.getYear(),sp.getMonth(),sp.getDate());
        int diff = cycle-pass;

        //설정된 주기
        mv.addObject("cycle",cycle);

        //경과일
        mv.addObject("pass",pass);

        //설정된 주기와 실제 경과일간 차이 (0이되면 sprint_re로 넘어가야됨)
        mv.addObject("diff",diff);


        if (sp.getLevel() < 3)
            sprintRepository.updateLevelBySprintid(sprintid,3);

        if(diff <= 0){
           mv.addObject("msg","스프린트 기간이 끝났습니다. 스프린트 회고로 넘어가 주세요 !");
        }

        return mv;
    }

    @RequestMapping(value="/sprint_re/{sprintid}", method= RequestMethod.GET)
    public ModelAndView sprinpre_get(@PathVariable Long sprintid) throws Exception{
        ModelAndView mv = new ModelAndView("sprint_re");
        mv.addObject("sprintid",sprintid);

        Sprint sp = sprintRepository.findBySprintid(sprintid);

        if (sp.getLevel() < 4)
            sprintRepository.updateLevelBySprintid(sprintid,4);


        return mv;
    }





}
