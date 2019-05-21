package board.board.controller;

import board.board.model.Project;
import board.board.model.ProjectMember;
import board.board.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value="/project", method= RequestMethod.GET)
    public ModelAndView openProjectList(/*ModelMap model*/) throws Exception{
        ModelAndView mv = new ModelAndView("project");

        List<ProjectMember> list = projectService.selectProjectList();
        mv.addObject("list", list);


        return mv;
    }

    @RequestMapping(value="/project/write", method= RequestMethod.GET)
    public String openProjectWrite(/*ModelMap model*/) throws Exception{

        return "projectWrite";
    }

    @RequestMapping(value="jpa/board/write", method=RequestMethod.POST)
    public String writeBoard(Project project) throws Exception{
        projectService.saveProject( project);
        return "redirect:/project";
    }

    @RequestMapping(value="jpa/board/{projectidx}", method=RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("projectidx") int projectidx) throws Exception{
        ModelAndView mv = new ModelAndView("mainProject");
    /*
        Board board = boardService.selectBoardDetail(boardidx);
        mv.addObject("board", board);
    */
        return mv;
    }

}
