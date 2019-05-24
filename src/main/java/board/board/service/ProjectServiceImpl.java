package board.board.service;
import board.board.model.Project;
import board.board.model.ProjectMember;
import board.board.repository.ProjectMemberRepository;
import board.board.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements   ProjectService{

    @Autowired
    ProjectMemberRepository projectMemberRepository;
    @Autowired
    ProjectRepository projectRepository;

   public List<ProjectMember> selectProjectMemberList() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();
        return projectMemberRepository.findById(username);
    }

    public List<Project> selectProjectList(List<ProjectMember> list){
            int pidx;
        List<Project> plist = new ArrayList<>();
            for(ProjectMember pm : list){
                pidx = pm.getProjectidx();
                plist.add(projectRepository.findByProjectidx(pidx));
            }
        return plist;
    }

    public  void saveProject(Project project) {

        ProjectMember pm = new ProjectMember();
        int projectidx;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        project.setCreatorid(username);
        projectRepository.save(project);
        projectidx = projectRepository.findMaxProjectidx();
        pm.setId(username);
        pm.setProjectidx(projectidx);
        projectMemberRepository.save(pm);

    }

    public int pageCheck(int projectidx) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String pusername = projectMemberRepository.findidByProjectidx(projectidx);
        if(username.equals(pusername)){return 1;}

        return 0;
    }


}
