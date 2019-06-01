package board.board.service;


public interface SprintService {

    int findSprintLevel(int pidx) throws Exception;

    int dateCheck(int year, int month, int date) throws Exception;
}
