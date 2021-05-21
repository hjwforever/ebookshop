//package com.aruoxi.ebookshop;
//
//import com.aruoxi.ebookshop.common.RegexUtil;
//import com.aruoxi.ebookshop.domain.Book;
//import com.aruoxi.ebookshop.repository.BookRepository;
//import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
//import net.minidev.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.Resource;
//import java.util.LinkedHashMap;
//
//@SpringBootTest
//class EbookshopApplicationTests {
//	private static final Logger log = LoggerFactory.getLogger(EbookshopApplicationTests.class);
//
//	private static final Logger LOG = LoggerFactory.getLogger(EbookshopApplicationTests.class);
//	@Resource
//	private BookRepository bookRepository;
//	@Resource
//	private BookServiceImpl bookService;
//	@Resource
//	private RegexUtil regexUtil;
//
//	/**
//	 * 新建并保存书籍的测试
//	 */
//	@Test
//	void saveBook() {
//		Book book = new Book();
//		book.setBookName("测试书籍1");
//		book.setAuthor("张三");
//		book.setBookIntro("这是一本测试书籍");
//		Book savedBook = bookRepository.save(book);  // 保存书籍
//		LOG.info("savedBook = " + savedBook.toString());  // 打印结果
//	}
//
//	/**
//	 * 根据名字 模糊查找所有书籍
//	 */
//	@Test
//	void findAllBooksByName() {
//		// 分页数据， 第一页， 按照星数starts排序
//		Pageable pageRequest = PageRequest.of(0, 10,
//				Sort.by("starts").ascending());
//		// 查找名字带有"点"的所有书籍
//		Page<Book> books = bookRepository.findByNameLike("小王", pageRequest);
//		// 打印结果
//		LOG.info("savedBook = " + books);
//		books.map(book -> {
//			LOG.info(book.getBookName());
//			return null;
//		});
//	}
//
//	/**
//	 * 根据Id 删除指定书籍
//	 */
//	@Test
//	void deleteBookById() {
//			bookRepository.deleteById(1L);
//	}
//
//	/**
//	 * 根据Id 修改指定书籍 使用save函数(原实体id存在则更新，否则新建一条数据)
//	 */
//	@Test
//	void updateBookById() {
//		// 查看修改前的书籍
//		Book oldBook = bookRepository.findByBookId(2L);
//		LOG.info("oldBook:  " + oldBook);
//		LOG.info("oldBook Name:  " + oldBook.getBookName());
//
//		// 新建修改书籍的实体
//		Book newBook = new Book();
//		newBook.setBookId(2L);
//		newBook.setBookName("修改书籍");
//		bookRepository.save(newBook);
//
//		// 查看修改后的书籍
//		Book updatedBook = bookRepository.findByBookId(2L);
//		LOG.info("updatedBook:  " + updatedBook);
//		LOG.info("updatedBook Name:  " + updatedBook.getBookName());
//	}
//
//	/**
//	 * 测试注册及登录
//	 */
//	@Test
//	void restTemplateForRegistration() {
//		RestTemplate client = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//		JSONObject param = new JSONObject();
//		param.put("username", "registerTest");
//		param.put("email", "registerTest@qq.com");
//		param.put("password", "123456");
//
//		HttpEntity<JSONObject> formEntity = new HttpEntity<>(param, headers);
//
//		String result = client.postForObject("http://localhost:8081/api/auth/register", formEntity, String.class);
//		LOG.info("result: " + result);
//	}
//
//	@Test
//	void contextLoads() {
//		Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
//		Page<Book> books = bookRepository.findByNameLike("点", PageRequest.of(0, 2, sort));
//		LOG.info("books = " + books);
//	}
//
//	@Test
//	void testRegex() {
//		boolean a = regexUtil.isPageStartAndEnd("3-2");
//		boolean b = regexUtil.isPageStartAndEnd("1-2");
//		boolean c = regexUtil.isPageStartAndEnd("6");
//		boolean d = regexUtil.isPageStartAndEnd("1-3-6");
//		log.info("a = " + a);
//		log.info("b = " + b);
//		log.info("c = " + c);
//		log.info("d = " + d);
//	}
//
//	@Test
//	void testRegex2() {
//		boolean a = regexUtil.isNumber("3-2");
//		boolean b = regexUtil.isNumber("1-2");
//		boolean c = regexUtil.isNumber("6");
//		boolean d = regexUtil.isNumber("1-3-6");
//		regexUtil.isNumber("2");
//		log.info("a = " + a);
//		log.info("b = " + b);
//		log.info("c = " + c);
//		log.info("d = " + d);
//	}
//
//	/**
//	 * 管理员-测试admin权限
//	 */
//	@Test
//	void testAdminRoot() {
//		RestTemplate client = new RestTemplate();
//		HttpHeaders headers = new HttpHeaders();
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
////    headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIwNjU2NTYyLCJleHAiOjE2MjA3NDI5NjJ9.h6OvwkWJh8purA9Lj16gkKY_j1eIbhfYdMfF96-btQxrS0CjGJi9z9iiXrQObbFCgpOn0iqPDLP8SIFhAjJAgA");
//
//		JSONObject param = new JSONObject();
//		param.put("username", "hjw");
//		param.put("password", "123456");
//
//		HttpEntity<JSONObject> formEntity = new HttpEntity<>(param, headers);
//
//		JSONObject result = client.postForObject("http://localhost:8081/api/auth/login", formEntity, JSONObject.class);
//		LOG.info("result: " + result);
//
//
//		HttpHeaders headers1 = new HttpHeaders();
//		MediaType type1 = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers1.setContentType(type1);
//		headers1.add("Accept", "*/*");
//
//		RestTemplate client1 = new RestTemplate();
//		String token = ((LinkedHashMap<String,String>)result.get("data")).get("token");
//		headers1.add("Authorization", "Bearer " + token);
//		LOG.info("token: " + token);
//		LOG.info("headers1: " + headers1);
//
////    JSONObject param1 = new JSONObject();
//		HttpEntity formEntity1 = new HttpEntity(headers1);
//		String result1 = client1.getForObject("http://localhost:8081/api/books/30/pages/1-10", String.class, formEntity1);
//
//		LOG.info("admin_result: " + result1);
//	}
//}
