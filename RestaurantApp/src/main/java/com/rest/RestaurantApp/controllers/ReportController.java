package com.rest.RestaurantApp.controllers;

import com.rest.RestaurantApp.dto.ArticleReportDTO;
import com.rest.RestaurantApp.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/articleProfitDay/{year}/{month}/{day}")
    public ResponseEntity<ArticleReportDTO> articleProfitDayReport(@PathVariable("year") int year,
                                                @PathVariable("month") int month, @PathVariable("day") int day) {
        ArticleReportDTO articleReport = reportService.articleProfitDayReport(year, month, day);

        if(articleReport.getArticleProfits().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(articleReport, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/articleProfitMonth/{year}/{month}")
    public ResponseEntity<ArticleReportDTO> articleProfitDayReport(@PathVariable("year") int year,
                                                                   @PathVariable("month") int month) {
        ArticleReportDTO articleReport = reportService.articleProfitMonthReport(year, month);

        if(articleReport.getArticleProfits().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(articleReport, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/articleProfitYear/{year}")
    public ResponseEntity<ArticleReportDTO> articleProfitYearReport(@PathVariable("year") int year) {
        ArticleReportDTO articleReport = reportService.articleProfitYearReport(year);

        if(articleReport.getArticleProfits().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(articleReport, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/articleProfitQuarter/{year}/{quarter}")
    public ResponseEntity<ArticleReportDTO> articleProfitQuarterReport(@PathVariable("year") int year,
                                                                       @PathVariable("quarter") int quarter) {
        ArticleReportDTO articleReport = reportService.articleProfitQuarterReport(year, quarter);

        if(articleReport.getArticleProfits().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(articleReport, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/articleProfitBetweenDates/{dateFrom}/{dateTo}")
    public ResponseEntity<ArticleReportDTO> articleProfitBetweenDates(@PathVariable("dateFrom") Date dateFrom,
    																  @PathVariable("dateTo") Date dateTo) {
        ArticleReportDTO articleReport = reportService.articleProfitBetweenDates(dateFrom, dateTo);

        if(articleReport.getArticleProfits().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(articleReport, HttpStatus.OK);
    }
}
