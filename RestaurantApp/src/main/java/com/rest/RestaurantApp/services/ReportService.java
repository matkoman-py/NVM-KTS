package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.OrderedArticle;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.ArticleReportDTO;
import com.rest.RestaurantApp.dto.ProfitDTO;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.OrderedArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService implements IReportService {

    private OrderedArticleRepository orderedArticleRepository;

    public ReportService(ArticleRepository articleRepository, OrderedArticleRepository orderedArticleRepository) {
        this.orderedArticleRepository = orderedArticleRepository;
    }

    @Override
    public ArticleReportDTO articleProfitDayReport(int year, int month, int day) {
        Date date = new GregorianCalendar(year, month - 1, day).getTime();
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ArrayList<OrderedArticle> orderedArticles = new ArrayList<>(orderedArticleRepository.findAllWithOrder());

        orderedArticles = orderedArticles.stream().filter(article -> compareArticleDate(article, ldt)).collect(Collectors.toCollection(ArrayList::new));

        return calcArticlesEarnings(orderedArticles);
    }

    @Override
    public ArticleReportDTO articleProfitMonthReport(int year, int month) {
        ArrayList<OrderedArticle> orderedArticles = new ArrayList<>(orderedArticleRepository.findAllWithOrder());

        orderedArticles = orderedArticles.stream().filter(article -> compareMonths(article, year, month)).collect(Collectors.toCollection(ArrayList::new));

        return calcArticlesEarnings(orderedArticles);
    }

    @Override
    public ArticleReportDTO articleProfitYearReport(int year) {
        ArrayList<OrderedArticle> orderedArticles = new ArrayList<>(orderedArticleRepository.findAllWithOrder());

        orderedArticles = orderedArticles.stream().filter(article -> compareYears(article, year)).collect(Collectors.toCollection(ArrayList::new));

        return calcArticlesEarnings(orderedArticles);
    }

    @Override
    public ArticleReportDTO articleProfitQuarterReport(int year, int quarter) {
        ArrayList<OrderedArticle> orderedArticles = new ArrayList<>(orderedArticleRepository.findAllWithOrder());

        orderedArticles = orderedArticles.stream().filter(article -> compareQuarters(article, year, quarter)).collect(Collectors.toCollection(ArrayList::new));

        return calcArticlesEarnings(orderedArticles);
    }

    @Override
    public ArticleReportDTO articleProfitBetweenDates(Date dateFrom, Date dateTo) {
        ArrayList<OrderedArticle> orderedArticles = new ArrayList<>(orderedArticleRepository.findAllWithOrder());

        orderedArticles = orderedArticles.stream().filter(article -> compareArticleBetweenDates(article, dateFrom, dateTo)).collect(Collectors.toCollection(ArrayList::new));

        return calcArticlesEarnings(orderedArticles);
    }

    private boolean compareArticleBetweenDates(OrderedArticle article, Date dateFrom, Date dateTo) {
        LocalDate ldt = article.getOrder().getOrderDate().toLocalDate();
        LocalDate from = dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate to = dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (ldt.isAfter(from) || ldt.isEqual(from)) && (ldt.isBefore(to) || ldt.isEqual(to));
    }

    private boolean compareQuarters(OrderedArticle article, int year, int quarter) {
        LocalDateTime ldt = article.getOrder().getOrderDate();
        return (ldt.getYear() == year) && (ldt.getMonthValue() <= quarter * 3) && (ldt.getMonthValue() >= (quarter - 1) * 3);
    }

    private boolean compareYears(OrderedArticle article, int year) {
        LocalDateTime ldt = article.getOrder().getOrderDate();
        return (ldt.getYear() == year);
    }

    private boolean compareMonths(OrderedArticle article, int year, int month) {
        LocalDateTime ldt = article.getOrder().getOrderDate();
        return (ldt.getYear() == year) && (ldt.getMonthValue() == month);
    }

    private ArticleReportDTO calcArticlesEarnings(ArrayList<OrderedArticle> orderedArticles) {
        ArticleReportDTO articleReport = new ArticleReportDTO();

        for (OrderedArticle article : orderedArticles) {
            ArticleDTO articleDTO = new ArticleDTO(article.getArticle());
            ProfitDTO profit = articleReport.getArticleProfits().getOrDefault(articleDTO.getId(), new ProfitDTO());

            Double articleEarning = articleDTO.getSellingPrice() - articleDTO.getMakingPrice();

            profit.setEarning(profit.getEarning() + articleEarning);
            profit.setNumberOfOrders(profit.getNumberOfOrders() + 1);
            profit.setTotalMakingPrice(profit.getTotalMakingPrice() + articleDTO.getMakingPrice());
            profit.setTotalSellingPrice(profit.getTotalSellingPrice() + articleDTO.getSellingPrice());
            profit.setArticleName(articleDTO.getName());

            articleReport.getArticleProfits().put(articleDTO.getId(), profit);
        }
        return articleReport;
    }

    private boolean compareArticleDate(OrderedArticle article, LocalDateTime ldt) {
        LocalDateTime orderDate = article.getOrder().getOrderDate();
        return (orderDate.getDayOfYear() == ldt.getDayOfYear()) && (orderDate.getYear() == ldt.getYear());
    }
}
