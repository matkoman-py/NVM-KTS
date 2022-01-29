package com.rest.RestaurantApp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.rest.RestaurantApp.dto.ArticleReportDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ReportControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

	@Test
	public void testArticleProfitDay() {
		ResponseEntity<ArticleReportDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/report/articleProfitDay/2021/1/3", ArticleReportDTO.class);

		ArticleReportDTO result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3,result.getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitMonth() {
		ResponseEntity<ArticleReportDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/report/articleProfitMonth/2021/1", ArticleReportDTO.class);

		ArticleReportDTO result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(5,result.getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitYear() {
		ResponseEntity<ArticleReportDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/report/articleProfitYear/2021", ArticleReportDTO.class);

		ArticleReportDTO result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(6,result.getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitQuarter() {
		ResponseEntity<ArticleReportDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/report/articleProfitQuarter/2021/1", ArticleReportDTO.class);

		ArticleReportDTO result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(5,result.getArticleProfits().size());
	}
	
	@Test
	public void testArticleProfitBetweenDates() {
		ResponseEntity<ArticleReportDTO> responseEntity = restTemplate.withBasicAuth("manager_test", "test").getForEntity(
				"/api/report/articleProfitBetweenDates/Fri, 01 Jan 2021 01:00:00 GMT/Thu, 30 Dec 2021 01:00:00 GMT", ArticleReportDTO.class);

		ArticleReportDTO result = responseEntity.getBody();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(6,result.getArticleProfits().size());
	}
}
