package board.board.service;


import board.board.model.Project;
import board.board.model.Sprint;

import board.board.model.SprintBacklog;
import board.board.repository.SprintRepository;
import board.common.CurrentDate;
import board.common.DateDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SprintServiceImpl implements  SprintService {

    @Autowired
    SprintRepository sprintRepository;


    public int findSprintLevel(int pidx){

        Sprint sp = sprintRepository.findByProjectidx(pidx);

        return sp.getLevel();
    }

    public int dateCheck(int year, int month, int date) {

        CurrentDate currentDate = new CurrentDate();

        DateDiff dateDiff = new DateDiff();

        return dateDiff.GetDifferenceOfDate(currentDate.year(),currentDate.month(),currentDate.date(),year,month,date);


    }



}
