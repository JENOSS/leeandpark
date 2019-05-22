package board.board.service;

import board.board.model.Project;
import board.board.model.ProjectMember;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectMember> selectProjectMemberList() throws  Exception;
    List<Project> selectProjectList(List<ProjectMember> list) throws  Exception;
    void saveProject(Project project) throws Exception;
}