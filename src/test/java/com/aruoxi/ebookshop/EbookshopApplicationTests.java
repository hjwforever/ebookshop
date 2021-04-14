package com.aruoxi.ebookshop;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class EbookshopApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(EbookshopApplicationTests.class);

	@Resource
	private BookRepository bookRepository;

	/**
	 * 测试注册及登录
	 */
	@Test
	void restTemplateForRegistration() {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		JSONObject param = new JSONObject();
		param.put("username", "123");
		param.put("email", "123@qq.com");
		param.put("password", "123456");

		HttpEntity formEntity = new HttpEntity(param, headers);

		String result = client.postForObject("/api/register", formEntity, String.class);
	}

	@Test
	void contextLoads() {
		Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
		Page<Book> books = bookRepository.findByNameLike("点", PageRequest.of(0, 2, sort));
		LOG.info("books = " + books);
	}
}
