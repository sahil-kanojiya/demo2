package com.hello.advanced.programs;

//Report Generator using Factory & Strategy Pattern
//        Requirements:
//        Implement Factory Pattern to generate different types of reports (e.g., marks, attendance).
//        Use Strategy Pattern to switch between PDF and CSV generation logic dynamically.
//        Demonstrate good separation of concern using interfaces and patterns.

import java.util.List;

public class P4_ReportGenerator {

    public static void main(String[] args) {
        Report report = ReportConfigGenerationFactory.generateReport(Report.ReportType.ByMarks, Report.ReportStrategy.PDF);
        report.generate("/user/storage/");
    }

}

interface Report {

    void generate(String path);

    enum ReportType {
        ByMarks, ByAttendance
    }

    enum ReportStrategy {
        PDF, CSV
    }
}

class ReportByMarks implements Report {

    private final ReportGenerationStrategy reportGenerationStrategy;

    public ReportByMarks(ReportGenerationStrategy reportGenerationStrategy) {
        this.reportGenerationStrategy = reportGenerationStrategy;
    }

    public ReportStrategy getReportGenerationStrategy() {
        return reportGenerationStrategy.getStrategy();
    }

    @Override
    public void generate(String path) {
        //fetch data from db
        reportGenerationStrategy.produceReportFromTemplate(path, List.of(1, 2, 3));
    }
}

class ReportByAttendance implements Report {

    private final ReportGenerationStrategy reportGenerationStrategy;

    public ReportByAttendance(ReportGenerationStrategy reportGenerationStrategy) {
        this.reportGenerationStrategy = reportGenerationStrategy;
    }

    public ReportStrategy getReportGenerationStrategy() {
        return reportGenerationStrategy.getStrategy();
    }

    @Override
    public void generate(String path) {
        //fetch data from db
        reportGenerationStrategy.produceReportFromTemplate(path, List.of(1, 2, 3));
    }
}

interface ReportGenerationStrategy {

    void produceReportFromTemplate(String path, List<Integer> data);

    Report.ReportStrategy getStrategy();
}

class PDF_ReportGenerationStrategy implements ReportGenerationStrategy {

    private final Report.ReportStrategy strategy = Report.ReportStrategy.PDF;

    @Override
    public Report.ReportStrategy getStrategy() {
        return strategy;
    }

    @Override
    public void produceReportFromTemplate(String path, List<Integer> data) {
        System.out.println("report pdf file generated and saved at:" + path);
    }
}

class CSV_ReportGenerationStrategy implements ReportGenerationStrategy {

    private final Report.ReportStrategy strategy = Report.ReportStrategy.CSV;

    @Override
    public Report.ReportStrategy getStrategy() {
        return strategy;
    }

    @Override
    public void produceReportFromTemplate(String path, List<Integer> data) {
        System.out.println("report csv file generated and saved at:" + path);
    }
}

class ReportConfigGenerationFactory {

    public static Report generateReport(Report.ReportType type, Report.ReportStrategy strategy) {
        ReportGenerationStrategy reportGenerationStrategy = switch (strategy) {
            case PDF -> new PDF_ReportGenerationStrategy();
            case CSV -> new CSV_ReportGenerationStrategy();
            default -> new PDF_ReportGenerationStrategy();
        };
        return switch (type) {
            case ByMarks -> new ReportByMarks(reportGenerationStrategy);
            case ByAttendance -> new ReportByAttendance(reportGenerationStrategy);
            default -> new ReportByMarks(reportGenerationStrategy);
        };
    }

}