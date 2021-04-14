package com.aruoxi.ebookshop;

import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;

@SpringBootTest
class EbookshopApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(EbookshopApplicationTests.class);

	@Resource
	private BookRepository bookRepository;

	@Test
	void contextLoads() {
		Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
		Page<Book> books = bookRepository.findByNameLike("点", PageRequest.of(0, 2, sort));
		LOG.info("books = " + books);
	}
}
