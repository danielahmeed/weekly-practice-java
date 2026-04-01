# Student Grade Report Generator

This project reads student grades from a CSV file, processes the data with Streams, and writes an HTML report to disk.

## Input format

The default input file is `data/students.csv`.

```csv
name,subject,score
Alice,Math,94
Bob,History,78
```

## Run

From the project folder, compile and run with Java 17 or newer:

```powershell
$sources = Get-ChildItem -Recurse -Filter *.java -Path src/main/java | ForEach-Object { $_.FullName }
javac -d out $sources
java -cp out com.weekly.grades.App
```

The report is written to `build/student-grade-report.html`.
