package com.timesheet.utility;

import lombok.Builder;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;


@Builder
public class WorkDay {
    private Date date;

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeOfArrival() {
        return new StringBuilder()
                .append(timeOfArrival.getHours())
                .append(":")
                .append(timeOfArrival.getMinutes())
                .toString();
    }

    public void setTimeOfArrival(Time timeOfArrival) {
        this.timeOfArrival = timeOfArrival;
    }

    public String getTimeOfDeparture() {
        return new StringBuilder()
                .append(timeOfDeparture.getHours())
                .append(":")
                .append(timeOfDeparture.getMinutes())
                .toString();
    }

    public void setTimeOfDeparture(Time timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public String getHoursWorked() {
        return new StringBuilder()
                .append(hoursWorked.getHours())
                .append(":")
                .append(hoursWorked.getMinutes())
                .toString();
    }

    public void setHoursWorked(Time hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getWasLunchBreak() {
        return wasLunchBreak;
    }

    public void setWasLunchBreak(String wasLunchBreak) {
        this.wasLunchBreak = wasLunchBreak;
    }

    private Time timeOfArrival;
    private Time timeOfDeparture;
    private Time hoursWorked;
    private String wasLunchBreak;

    public WorkDay(Date date, Time timeOfArrival, Time timeOfDeparture, String wasLunchBreak) {
        this.date = date;
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
        this.wasLunchBreak = wasLunchBreak;
    }

    public WorkDay(String date, String timeOfArrival, String timeOfDeparture, String wasLunchBreak) {
        String[] dateArray = date.split("-");
        this.date = new Date(
                Integer.parseInt(removeCharFromString(dateArray[0],'\''))-1900,
                Integer.parseInt(dateArray[1]),
                Integer.parseInt(removeCharFromString(dateArray[2], '\'')));
        String[] timeOfArrivalArray = timeOfArrival.split(":");
        this.timeOfArrival = new Time(
                Integer.parseInt(removeCharFromString(timeOfArrivalArray[0], '\'')),
                Integer.parseInt(removeCharFromString(timeOfArrivalArray[1], '\'')),
                0);
        String[] timeOfDepartureArray = timeOfDeparture.split(":");
        this.timeOfDeparture = new Time(
                Integer.parseInt(removeCharFromString(timeOfDepartureArray[0], '\'')),
                Integer.parseInt(removeCharFromString(timeOfDepartureArray[1], '\'')),
                0);
        this.hoursWorked = calculateTimeAtWork();

        this.wasLunchBreak = removeCharFromString(wasLunchBreak, '\'') ;


        calculateTimeAtWork();
    }

    public WorkDay(Date date, Time timeOfArrival, Time timeOfDeparture, Time hoursWorked, String wasLunchBreak) {
        this.date = date;
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
        this.hoursWorked = calculateTimeAtWork();
        this.wasLunchBreak = wasLunchBreak;
    }

    @Override
    public String toString() {
        Time timeAtWork = calculateTimeAtWork();
        return  date + " " +
                timeOfArrival.getHours() + ":" + timeOfArrival.getMinutes() + " " +
                timeOfDeparture.getHours() + ":" + timeOfDeparture.getMinutes() + " " +
                timeAtWork.getHours() + ":" + timeAtWork.getMinutes() + " " +
                wasLunchBreak ;
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

        if(Objects.equals(wasLunchBreak, new String("Yes"))) {
            hoursAtWork--;
        }
        this.hoursWorked = new Time(hoursAtWork, minutesAtWork, 0);

        return new Time(hoursAtWork, minutesAtWork, 0);
    }

    private boolean checkInterval(int num ,int a, int b){
        return a <= num && num <= b;
    }

    private String removeCharFromString(String inputWord, char thingToRemove){
        char[] charArray = inputWord.toCharArray();
        StringBuilder result = new StringBuilder();

        for (char letter: charArray) {
            if(letter != thingToRemove)result.append(letter);
        }

        return result.toString();
    }
}
