package com.weekly.grades;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class App {
    private static final Path INPUT = Path.of("data", "students.csv");
    private static final Path OUTPUT = Path.of("build", "student-grade-report.html");

    private App() {
    }

    public static void main(String[] args) {
        CompletableFuture<List<StudentScore>> studentsFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return CsvReader.readStudents(INPUT);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to read CSV input", exception);
            }
        });

        CompletableFuture<Path> reportFuture = studentsFuture
                .thenApply(GradeReport::createHtml)
                .thenCompose(html -> CompletableFuture.runAsync(() -> {
                    try {
                        writeHtml(OUTPUT, html);
                    } catch (IOException exception) {
                        throw new RuntimeException("Failed to write HTML report", exception);
                    }
                }).thenApply(ignored -> OUTPUT));

        Path reportPath = reportFuture.join();
        System.out.println("Report written to: " + reportPath.toAbsolutePath());
    }

    private static void writeHtml(Path output, String html) throws IOException {
        Files.createDirectories(output.getParent());
        Files.writeString(output, html, StandardCharsets.UTF_8);
    }
}
