package com.aruoxi.ebookshop.controller;

import cn.leancloud.AVFile;
import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.BookSearchDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

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
        model.addAttribute("books", books.getContent());
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
    public String aaa(Model model, @RequestParam Integer newPageNum, BookSearchDto bookSearchDto) {
        log.info("model = " + model);
        log.info("bookSearchDto = " + bookSearchDto);
        Page<Book> books;
        Integer pageSize = bookSearchDto.getPageSize();
        String bookName = bookSearchDto.getBookName();
        log.info("pageSize = " + pageSize);
        log.info("bookName = " + bookName);
        books = bookService.findPage(newPageNum, pageSize, bookName);
        log.info("books = " + books);
        model.addAttribute("books", books.getContent());
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

    @GetMapping(value = "/upload")
    public String upload() {
        return "upload";
    }

    // 上传文件会自动绑定到MultipartFile中
    @PostMapping(value = "/upload")
    @ResponseBody
    public CommonResult upload(HttpServletRequest request,
                               @RequestParam("file") MultipartFile multipartFile) throws Exception {
        if (multipartFile != null) {
            String filename = multipartFile.getOriginalFilename();
            AVFile file = new AVFile(filename, multipartFile.getBytes());
            file.saveInBackground(true).subscribe(new Observer<AVFile>() {
                public void onSubscribe(Disposable disposable) {}
                public void onNext(AVFile file) {
                    log.debug("文件保存完成 objectId：" + file.getObjectId());
                }
                public void onError(Throwable throwable) {
                    log.debug("failed to get data. cause: " + throwable.getMessage());
                }
                public void onComplete() {}
            });;
            log.info("file.getUrl()" + file.getUrl());
            log.info("file" + file);
            String newFilename;
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                newFilename = filename.substring(0, dot);
            } else {
                newFilename = filename;
            }

            Book book = new Book();
            book.setBookName(newFilename);
            book.setBookUri(file.getUrl());
            bookService.save(book);

            return CommonResult.success(file.getUrl());
        }
        return CommonResult.fail(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    /**
     * 获取下载链接 如 { "url": "http://example.com/books/book1.txt" }
     * @param request
     * @param userAgent
     * @param bookId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/downloadUrl")
    public CommonResult download(HttpServletRequest request,
                                           @RequestHeader("User-Agent") String userAgent,
                                           @RequestParam("bookId") Long bookId,
                                           Model model) throws Exception {
        return getBookUrl(bookId, bookRepository);
    }

    public static CommonResult getBookUrl(@RequestParam("bookId") Long bookId, BookRepository bookRepository) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("url",book.getBookUri());
            return CommonResult.success(map);
        }
        return CommonResult.fail(HttpStatus.NOT_FOUND,HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download1(HttpServletRequest request,
                                           @RequestHeader("User-Agent") String userAgent,
                                           @RequestParam("bookId") Long bookId,
                                           Model model) throws Exception {
//        AVFile.getQuery()
        Book book = bookRepository.findById(bookId).orElse(null);
        // 下载文件路径
        String path = book.getBookUri();
        // 构建File
        String filename = book.getBookName();
        log.info("path= " + path);
        log.info("File.separator= " + File.separator);
        log.info(filename);
//        File file = new File(path + File.separator + filename);
        File file = new File(path);
        // ok表示Http协议中的状态 200
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        // 内容长度
        builder.contentLength(file.length());
        // application/octet-stream ： 二进制流数据（最常见的文件下载）。
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用URLDecoder.decode对文件名进行解码
        filename = URLEncoder.encode(filename, "UTF-8");
        // 设置实际的响应文件名，告诉浏览器文件要用于【下载】、【保存】attachment 以附件形式
        // 不同的浏览器，处理方式不同，要根据浏览器版本进行区别判断
        if (userAgent.indexOf("MSIE") > 0) {
            // 如果是IE，只需要用UTF-8字符集进行URL编码即可
            builder.header("Content-Disposition", "attachment; filename=" + filename);
        } else {
            // 而FireFox、Chrome等浏览器，则需要说明编码的字符集
            // 注意filename后面有个*号，在UTF-8后面有两个单引号！
            builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(file));
    }

}
