package com.weekly.grades;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class GradeReport {
    private GradeReport() {
    }

    public static String createHtml(List<StudentScore> students) {
        Objects.requireNonNull(students, "students");

        List<StudentScore> sortedStudents = students.stream()
                .sorted(Comparator.comparing(StudentScore::name).thenComparing(StudentScore::subject))
                .toList();

        double averageScore = students.stream()
                .mapToInt(StudentScore::score)
                .average()
                .orElse(0.0);

        StudentScore topStudent = students.stream()
                .max(Comparator.comparingInt(StudentScore::score))
                .orElseThrow(() -> new IllegalArgumentException("At least one student is required"));

        Map<GradeBand, Long> bandCounts = students.stream()
                .collect(Collectors.groupingBy(StudentScore::band, Collectors.counting()));

        String rows = sortedStudents.stream()
                .map(student -> """
                        <tr>
                            <td>%s</td>
                            <td>%s</td>
                            <td>%d</td>
                            <td><span class="badge %s">%s</span></td>
                        </tr>
                        """.formatted(
                        escapeHtml(student.name()),
                        escapeHtml(student.subject()),
                        student.score(),
                        student.band().cssClass(),
                        student.band().label()))
                .collect(Collectors.joining(System.lineSeparator()));

        String summaryCards = bandCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> """
                        <div class="card">
                            <div class="card-value">%d</div>
                            <div class="card-label">%s</div>
                        </div>
                        """.formatted(entry.getValue(), entry.getKey().label()))
                .collect(Collectors.joining(System.lineSeparator()));

        return """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Student Grade Report</title>
                    <style>
                        :root {
                            color-scheme: light;
                            --bg: #f6f1e8;
                            --panel: #ffffff;
                            --ink: #1f2933;
                            --muted: #52606d;
                            --accent: #0f766e;
                            --accent-soft: #ccfbf1;
                            --warn: #b45309;
                            --danger: #b91c1c;
                            --border: #d9e2ec;
                        }

                        body {
                            margin: 0;
                            font-family: Segoe UI, Arial, sans-serif;
                            background: linear-gradient(135deg, #f6f1e8 0%%, #edf5f7 50%%, #f8fafc 100%%);
                            color: var(--ink);
                        }

                        .shell {
                            max-width: 1100px;
                            margin: 0 auto;
                            padding: 40px 24px 64px;
                        }

                        .hero {
                            display: grid;
                            gap: 16px;
                            margin-bottom: 28px;
                        }

                        h1 {
                            margin: 0;
                            font-size: clamp(2rem, 4vw, 3.4rem);
                            letter-spacing: -0.04em;
                        }

                        .subtitle {
                            margin: 0;
                            max-width: 68ch;
                            color: var(--muted);
                            line-height: 1.6;
                        }

                        .metrics {
                            display: grid;
                            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
                            gap: 16px;
                            margin-bottom: 28px;
                        }

                        .card, .panel {
                            background: var(--panel);
                            border: 1px solid var(--border);
                            border-radius: 18px;
                            box-shadow: 0 16px 40px rgba(15, 23, 42, 0.06);
                        }

                        .card {
                            padding: 20px;
                        }

                        .card-value {
                            font-size: 2rem;
                            font-weight: 700;
                            color: var(--accent);
                        }

                        .card-label {
                            margin-top: 6px;
                            color: var(--muted);
                        }

                        .panel {
                            padding: 20px;
                        }

                        .panel h2 {
                            margin-top: 0;
                        }

                        table {
                            width: 100%%;
                            border-collapse: collapse;
                        }

                        th, td {
                            padding: 14px 12px;
                            border-bottom: 1px solid var(--border);
                            text-align: left;
                        }

                        th {
                            color: var(--muted);
                            font-size: 0.92rem;
                            text-transform: uppercase;
                            letter-spacing: 0.06em;
                        }

                        .badge {
                            display: inline-flex;
                            align-items: center;
                            padding: 6px 12px;
                            border-radius: 999px;
                            font-size: 0.85rem;
                            font-weight: 700;
                        }

                        .grade-excellent { background: #dcfce7; color: #166534; }
                        .grade-passing { background: #dbeafe; color: #1d4ed8; }
                        .grade-needs-improvement { background: #fee2e2; color: #991b1b; }

                        .meta {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 12px 20px;
                            margin: 12px 0 0;
                            color: var(--muted);
                        }

                        .meta strong {
                            color: var(--ink);
                        }
                    </style>
                </head>
                <body>
                    <main class="shell">
                        <section class="hero">
                            <h1>Student Grade Report</h1>
                            <p class="subtitle">
                                Generated from CSV input using Java Streams and asynchronous file I/O.
                                The report summarizes all grades, highlights the top student, and groups results by band.
                            </p>
                            <div class="meta">
                                <div><strong>Total students:</strong> %d</div>
                                <div><strong>Average score:</strong> %s</div>
                                <div><strong>Top student:</strong> %s (%d)</div>
                            </div>
                        </section>

                        <section class="metrics">
                            %s
                        </section>

                        <section class="panel">
                            <h2>All Students</h2>
                            <table>
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Subject</th>
                                        <th>Score</th>
                                        <th>Band</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    %s
                                </tbody>
                            </table>
                        </section>
                    </main>
                </body>
                </html>
                """.formatted(
                students.size(),
                String.format(Locale.US, "%.1f", averageScore),
                escapeHtml(topStudent.name()),
                topStudent.score(),
                summaryCards,
                rows);
    }

    private static String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
