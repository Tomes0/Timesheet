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
import java.util.List;

public class CsvUtility {
    static final String[] header = {"Date", "Arrival time", "Departure time", "Hours worked", "Lunch-break"};

    //private static final Path path = Paths.get("src/main/java/org/example/csv/hours.csv");
    private static Path path;

    @SneakyThrows
    public CsvUtility() {
        path = Paths.get(ClassLoader.getSystemResource("csv/hours.csv").toURI());
    }

    public static List<String[]> readAllLines() throws Exception {
        try (Reader reader = Files.newBufferedReader(path)) {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();
            try (
                    CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build()
            ) {
                return csvReader.readAll();
            }
        }
    }

    public static void writeAllLines(List<String[]> lines) throws Exception {
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
