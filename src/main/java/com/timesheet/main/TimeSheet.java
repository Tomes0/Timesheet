package com.timesheet.main;

import com.timesheet.utility.CsvUtility;
import com.timesheet.utility.WorkDay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeSheet extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TimeSheet.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch();
        long epochDate = 1669368317000L;
        Date date = new Date(epochDate);
        WorkDay workDay = new WorkDay(date, new Time(8,15,0), new Time(16,44,0), false);
        List<String[]> workDays =  new ArrayList<>();
        workDays.add(workDay.toString().split(" "));

        CsvUtility.writeAllLines(workDays);
        System.out.println(workDay);
        System.out.println(CsvUtility.readAllLines());
    }
}