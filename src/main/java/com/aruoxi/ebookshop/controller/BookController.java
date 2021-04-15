package com.aruoxi.ebookshop.controller;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.BookSearchDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/books")
public class BookController {

  private static final Logger log = LoggerFactory.getLogger(BookController.class);
  private static final Logger LOG = LoggerFactory.getLogger(BookController.class);
  @Resource
  private BookServiceImpl bookService;
  @Resource
  private BookRepository bookRepository;
  @Value("${ebookshop.title}")
  private String title;

  @GetMapping
  public String getBooks(Model model, BookSearchDto bookSearchDto, HttpServletRequest request) {

    @SuppressWarnings("unchecked")

    List<String> logs = (List<String>) request.getSession().getAttribute("LOGS_SESSION");
    //check if notes is present in session or not
    if (logs == null) {
      logs = new ArrayList<>();
      // if notes object is not present in session, set notes in the request session
      request.getSession().setAttribute("LOGS_SESSION", logs);
    }

    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    //String log = request.getBookPrincipal().getName() + "/" + timeStamp + "/" + "list books";
    String log = timeStamp + "/" + "list books";
    logs.add(log);
    request.getSession().setAttribute("LOGS_SESSION", logs);

    Page<Book> books = bookService.findPage(bookSearchDto.getPageNum(), bookSearchDto.getPageSize(), bookSearchDto.getBookName());
    model.addAttribute("books",books.getContent());
    LOG.info("总分页数据 = " + books);
    LOG.info("总页数  " + books.getTotalPages());
    LOG.info("-----------------该页数据------------------------");
    LOG.info("该页前面的数据条数 = " + books.getPageable().getOffset());
    LOG.info("该页的序号(从0开始) = " + books.getPageable().getPageNumber());
    LOG.info("该页的序号(从0开始) = " + books.getNumber());
    LOG.info("该页的数据条数  " + books.stream().count());
    LOG.info("该页大小 = " + books.getPageable().getPageSize());
    LOG.info("该页大小  " + books.getSize());
    LOG.info("是否有上一页  " + books.hasPrevious());
    LOG.info("是否有下一页  " + books.hasNext());
    LOG.info("是否是第一页  " + books.isFirst());
    LOG.info("是否是最后一页  " + books.isLast());
    LOG.info("前一页或第一页为  " + books.nextOrLastPageable());
    LOG.info("后一页或最后一页为  " + books.previousOrFirstPageable());
//    long count = bookRepository.count();
//    boolean hasPrev = pageNumber > 1;
//    boolean hasNext = ((long) pageNumber * rows) < count;
//    model.addAttribute("books", books);
    model.addAttribute("hasPre", books.hasPrevious());
    model.addAttribute("hasNext", books.hasNext());
    model.addAttribute("pageNum", books.getNumber() + 1);
    model.addAttribute("pageSize", books.getSize());
    model.addAttribute("searchBookName", bookSearchDto.getBookName());
//    model.addAttribute("prevPage", books.previousOrFirstPageable());
//    model.addAttribute("nextPage", books.nextOrLastPageable());
    return "home";
  }

  @RequestMapping("/refresh")
  public String aaa(Model model, @RequestParam Integer newPageNum,BookSearchDto bookSearchDto) {
    log.info("model = " + model);
    log.info("bookSearchDto = " + bookSearchDto);
    Page<Book> books;
    Integer pageSize = bookSearchDto.getPageSize();
    String bookName = bookSearchDto.getBookName();
    log.info("pageSize = " + pageSize);
    log.info("bookName = " + bookName);
    books = bookService.findPage(newPageNum , pageSize, bookName);
    log.info("books = " + books);
    model.addAttribute("books",books.getContent());
    model.addAttribute("hasPre", books.hasPrevious());
    model.addAttribute("hasNext", books.hasNext());
    model.addAttribute("pageNum", books.getNumber() + 1);
    model.addAttribute("pageSize", books.getSize());
    model.addAttribute("searchBookName", bookSearchDto.getBookName());
    return "home::bookList";
  }

//  @GetMapping("/test")
//  public String findAll(Model model) {
//    Page<Book> bookp=bookService.findPage(1, 5, "");
//    model.addAttribute("book",bookp);
//    return "test";
//  }

}
