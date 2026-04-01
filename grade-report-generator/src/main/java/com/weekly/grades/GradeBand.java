package com.weekly.grades;

public enum GradeBand {
    EXCELLENT("Excellent", "grade-excellent"),
    PASSING("Passing", "grade-passing"),
    NEEDS_IMPROVEMENT("Needs Improvement", "grade-needs-improvement");

    private final String label;
    private final String cssClass;

    GradeBand(String label, String cssClass) {
        this.label = label;
        this.cssClass = cssClass;
    }

    public String label() {
        return label;
    }

    public String cssClass() {
        return cssClass;
    }

    public static GradeBand fromScore(int score) {
        if (score >= 90) {
            return EXCELLENT;
        }
        if (score >= 70) {
            return PASSING;
        }
        return NEEDS_IMPROVEMENT;
    }
}
