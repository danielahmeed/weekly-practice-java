package com.weekly.grades;

public record StudentScore(String name, String subject, int score) {
    public StudentScore {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("subject cannot be blank");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("score must be between 0 and 100");
        }
    }

    public GradeBand band() {
        return GradeBand.fromScore(score);
    }
}
