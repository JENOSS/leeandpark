package board.board.controller;

import board.board.model.Project;
import board.board.model.ProjectMember;
import board.board.service.ProjectService;
import board.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/project", method= RequestMethod.GET)
    public ModelAndView openProjectList() throws Exception{
        ModelAndView mv = new ModelAndView("projectHome");

        List<ProjectMember> list = projectService.selectProjectMemberList();
        List<Project> plist = projectService.selectProjectList(list);

        String name = userService.selectUsername();
        mv.addObject("list", plist);
        mv.addObject(name);
        return mv;
    }

    @RequestMapping(value="/project/write", method= RequestMethod.GET)
    public String openProjectWrite() throws Exception{

        return "projectWrite";
    }


    @RequestMapping(value="/project/write", method=RequestMethod.POST)
    public String writeBoard(Project project) throws Exception{
        projectService.saveProject(project);
        return "redirect:/project";
    }

    @RequestMapping(value="project/{projectidx}", method=RequestMethod.GET)
    public ModelAndView openProjectDetail(@PathVariable int projectidx) throws Exception{
        int check =0;
        check = projectService.pageCheck(projectidx);
        if(check == 1) {
            ModelAndView mv = new ModelAndView("index");
            mv.addObject(projectidx);
            //여기서 projectidx로 게시판 가져오기?
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView("welcome");
            return mv;
        }
    }




}
