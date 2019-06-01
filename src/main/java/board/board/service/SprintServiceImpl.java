package board.board.service;


import board.board.model.Sprint;
import board.board.repository.SprintRepository;
import board.common.CurrentDate;
import board.common.DateDiff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintServiceImpl implements SprintService {

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
