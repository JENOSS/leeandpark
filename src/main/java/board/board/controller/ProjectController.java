package board.board.controller;

import board.board.model.Project;
import board.board.model.ProjectMember;
import board.board.model.User;
import board.board.service.ProjectService;
import board.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

        User user = userService.selectUsername();
        mv.addObject("list", plist);
        mv.addObject(user);
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

    @GetMapping("/project/{projectidx}/findProjectMember")
    public ModelAndView initFindForm(Map<String, Object> model,ModelAndView mv,@PathVariable int projectidx) {
        model.put("user", new User());
        mv.setViewName("findProjectMember");
        mv.addObject(model);
        mv.addObject(projectidx);
        return mv;
    }
    @GetMapping("/project/{projectidx}/projectMember")
    public ModelAndView processFindForm(User user, BindingResult result, Map<String, Object> model,ModelAndView mv,@PathVariable int projectidx) {

        // allow parameterless GET request for /owners to return all records
        if (user.getUsername() == null) {
            user.setUsername(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<User> results = this.userService.findByName(user.getUsername());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("username", "notFound", "not found");
            mv.setViewName("findProjectMember");
            return mv;
        } //else if (results.size() == 1) {
            // 1 owner found
            //user = results.iterator().next();
           // return "redirect:/owners/" + owner.getId();
        //}
        else {
            // multiple owners found
            model.put("selections", results);
            mv.addObject(model);
            mv.addObject(projectidx);
            mv.setViewName("projectmemberList");
            return mv;
        }
    }

    @GetMapping("/project/{projectidx}/{username}")
    public String addProjectMember(@PathVariable int projectidx,@PathVariable String username) {
        int check = projectService.isMember(projectidx,username);
        if(check == 0)
            projectService.addMember(projectidx,username);
        return "redirect:/project";
    }
}
