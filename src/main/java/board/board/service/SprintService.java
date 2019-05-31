package board.board.service;


import board.board.model.Sprint;

public interface SprintService {

    int findSprintLevel(int pidx) throws Exception;

    int dateCheck(int year, int month, int date) throws Exception;
}
