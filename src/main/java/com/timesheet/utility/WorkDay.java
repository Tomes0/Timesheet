package com.timesheet.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Builder
public class WorkDay {
    Date date;
    Time timeOfArrival;
    Time timeOfDeparture;

    boolean wasLunchBreak;


    @Override
    public String toString() {
        Time timeAtWork = calculateTimeAtWork();
        return  date + " " +
                timeOfArrival.getHours() + ":" + timeOfArrival.getMinutes() + " " +
                timeOfDeparture.getHours() + ":" + timeOfDeparture.getMinutes() + " " +
                timeAtWork.getHours() + ":" + timeAtWork.getMinutes() + " " +
                (wasLunchBreak? "Yes": "No") ;
    }


    public Time calculateTimeAtWork(){
        long timeAtWorkInMilliseconds = timeOfDeparture.getTime() - timeOfArrival.getTime();
        int hoursAtWork = (int) (timeAtWorkInMilliseconds / 3600000);
        int minutesAtWork = (int) (timeAtWorkInMilliseconds - (hoursAtWork * 3600000)) / 60000 ;

        if(checkInterval(minutesAtWork, 0, 14)){
            minutesAtWork = 0;
        }
        if(checkInterval(minutesAtWork, 15, 44)){
            minutesAtWork = 30;
        }
        if(checkInterval(minutesAtWork, 45, 60)){
            minutesAtWork = 0;
            hoursAtWork++;
        }

        if(wasLunchBreak) {
            hoursAtWork--;
        }

        return new Time(hoursAtWork, minutesAtWork, 0);
    }

    private boolean checkInterval(int num ,int a, int b){
        return a <= num && num <= b;
    }
}
