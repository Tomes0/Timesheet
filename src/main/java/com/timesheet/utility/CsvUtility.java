package com.timesheet.utility;

import com.opencsv.*;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtility {
    final String[] header = {"Date", "Arrival time", "Departure time", "Hours worked", "Lunch-break"};

    private Path path;

    public CsvUtility(Path path) {
        this.path = path;
    }

    @SneakyThrows
    public List<WorkDay> readLineByLine(){
        List<WorkDay> workDayList = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(path)) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1).build()) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    workDayList.add(new WorkDay(
                            line[0],
                            line[1],
                            line[2],
                            line[4]));
                }
            }
        }

        return workDayList;
    }

    public void writeAllLines(List<String[]> lines) throws Exception {
        try (
                CSVWriter writer = new CSVWriter(
                        new FileWriter(path.toString()),
                        ',',
                        '\'')) {
            writer.writeNext(header);
            writer.writeAll(lines);
        }
    }


}
