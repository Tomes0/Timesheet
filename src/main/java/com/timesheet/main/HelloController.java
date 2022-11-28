package com.timesheet.main;

import com.timesheet.utility.CsvUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloController {
    @FXML
    private ListView listView ;


    @SneakyThrows
    public HelloController() {
        Path path = Paths.get("hours.csv");
        CsvUtility csv = new CsvUtility(path);

        ObservableList<String[]> days = FXCollections.observableArrayList(csv.readAllLines());

        this.listView = new ListView<String[]>(days);


    }
}