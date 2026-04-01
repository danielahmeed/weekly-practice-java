package com.weekly.grades;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class CsvReader {
    private CsvReader() {
    }

    public static List<StudentScore> readStudents(Path input) throws IOException {
        try (var lines = Files.lines(input, StandardCharsets.UTF_8)) {
            return lines
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(CsvReader::parseStudent)
                    .collect(Collectors.toList());
        }
    }

    private static StudentScore parseStudent(String line) {
        String[] columns = line.split(",");
        if (columns.length != 3) {
            throw new IllegalArgumentException("Invalid CSV row: " + line);
        }

        String name = Objects.requireNonNull(columns[0]).trim();
        String subject = Objects.requireNonNull(columns[1]).trim();
        int score = Integer.parseInt(columns[2].trim());
        return new StudentScore(name, subject, score);
    }
}
