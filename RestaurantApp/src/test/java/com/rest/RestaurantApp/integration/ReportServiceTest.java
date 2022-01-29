package com.rest.RestaurantApp.integration;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.rest.RestaurantApp.services.ReportService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ReportServiceTest {

	@Autowired
	private ReportService reportService;
	
	@Test
	public void testArticleProfitDayReport() {
		assertEquals(3,reportService.articleProfitDayReport(2021, 1, 3).getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitMonthReport() {
		assertEquals(5,reportService.articleProfitMonthReport(2021, 1).getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitYearReport() {
		assertEquals(6,reportService.articleProfitYearReport(2021).getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitQuarterReport() {
		assertEquals(5,reportService.articleProfitQuarterReport(2021, 1).getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitBetweenDates() {
		assertEquals(0,reportService.articleProfitBetweenDates(new Date(2022,1,1), new Date(2019,1,1)).getArticleProfits().size());
	}
}
