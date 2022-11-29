package com.timesheet.main;

import com.timesheet.utility.CsvUtility;
import com.timesheet.utility.WorkDay;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;

public class TimeSheet extends Application {
    @SneakyThrows
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TimeSheet.class.getResource("main.fxml"));

        Label label = new Label("Timesheet:");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        label.setFont(font);

        Path path = Paths.get("C:\\Users\\kutlak.tamas\\Documents\\Timesheet\\src\\main\\java\\com\\timesheet\\main\\hours.csv");
        CsvUtility csv = new CsvUtility(path);

        Scene scene = new Scene(fxmlLoader.load(), 650, 400);
        TableView<WorkDay> table = (TableView<WorkDay>)scene.lookup("#tableView");
        ObservableList<WorkDay> data = FXCollections.observableArrayList();
        data.addAll(csv.readLineByLine());

        TableColumn<WorkDay, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDate()));
        dateCol.setPrefWidth(100);
        TableColumn<WorkDay, String> arrivalCol = new TableColumn<>("Arrival");
        arrivalCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTimeOfArrival()));
        TableColumn<WorkDay, String> departureCol = new TableColumn<>("Departure");
        departureCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTimeOfDeparture()));
        TableColumn<WorkDay, String> hoursWorkedCol = new TableColumn<>("Hours worked");
        hoursWorkedCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getHoursWorked()));
        TableColumn<WorkDay, String> lunchBreakCol = new TableColumn<>("Lunch-break");
        lunchBreakCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getWasLunchBreak()));





        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.getColumns().addAll(dateCol,arrivalCol,departureCol,hoursWorkedCol,lunchBreakCol);

        table.setItems(data);


        stage.setTitle("Timesheet");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch();



    }
}