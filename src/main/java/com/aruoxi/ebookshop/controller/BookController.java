package com.aruoxi.ebookshop.controller;

import com.aruoxi.ebookshop.controller.dto.BookSearchDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
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

    //String log = request.getUserPrincipal().getName() + "/" + timeStamp + "/" + "list books";
    String log = timeStamp + "/" + "list books";
    logs.add(log);
    request.getSession().setAttribute("LOGS_SESSION", logs);

    Page<Book> books = bookService.findPage(bookSearchDto.getPageNum(), bookSearchDto.getPageSize(), bookSearchDto.getBookName());
    model.addAttribute("books",books.getContent());
    LOG.info("show books = " + books);
    LOG.info("details  " + books.toString());
    LOG.info("getTotalPages  " + books.getTotalPages());
    LOG.info("count  " + books.stream().count());
    LOG.info("getSize  " + books.getSize());
    LOG.info("hasPrevious  " + books.hasPrevious());
    LOG.info("count  " + books.hasNext());
    LOG.info("isFirst  " + books.isFirst());
    LOG.info("isLast  " + books.isLast());
    LOG.info("nextOrLastPageable  " + books.nextOrLastPageable());
    LOG.info("previousOrFirstPageable  " + books.previousOrFirstPageable());
//    long count = bookRepository.count();
//    boolean hasPrev = pageNumber > 1;
//    boolean hasNext = ((long) pageNumber * rows) < count;
//    model.addAttribute("books", books);
//    model.addAttribute("hasPrev", hasPrev);
//    model.addAttribute("prev", pageNumber - 1);
//    model.addAttribute("hasNext", hasNext);
//    model.addAttribute("next", pageNumber + 1);
    return "home";
  }

}
