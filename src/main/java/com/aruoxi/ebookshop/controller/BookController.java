package com.aruoxi.ebookshop.controller;

import cn.leancloud.AVFile;
import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.BookSearchDto;
import com.aruoxi.ebookshop.controller.dto.BookUploadDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Value("${ebookshop.file.filePrefix}")
    private String filePrefix;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object user = auth.getPrincipal();
        boolean authenticated = auth.isAuthenticated();
        LOG.info("user" + user);


        Page<Book> books = bookService.findPage(new BookSearchDto(bookSearchDto.getBookName(), bookSearchDto.getPageNum(), bookSearchDto.getPageSize()));
        LOG.info("??????????????? = " + books);
        LOG.info("?????????  " + books.getTotalPages());
        LOG.info("-----------------????????????------------------------");
        LOG.info("??????????????????????????? = " + books.getPageable().getOffset());
        LOG.info("???????????????(???0??????) = " + books.getPageable().getPageNumber());
        LOG.info("???????????????(???0??????) = " + books.getNumber());
        LOG.info("?????????????????????  " + books.stream().count());
        LOG.info("???????????? = " + books.getPageable().getPageSize());
        LOG.info("????????????  " + books.getSize());
        LOG.info("??????????????????  " + books.hasPrevious());
        LOG.info("??????????????????  " + books.hasNext());
        LOG.info("??????????????????  " + books.isFirst());
        LOG.info("?????????????????????  " + books.isLast());
        LOG.info("????????????????????????  " + books.nextOrLastPageable());
        LOG.info("???????????????????????????  " + books.previousOrFirstPageable());
//    long count = bookRepository.count();
//    boolean hasPrev = pageNumber > 1;
//    boolean hasNext = ((long) pageNumber * rows) < count;
//    model.addAttribute("books", books);
        model.addAttribute("books", books.getContent());
        HashMap<Object, Object> map = new HashMap<>();
        int totalPages = books.getTotalPages();
        for (int i = 1; i <= totalPages; i++) {
            if (totalPages > 5 && i != 1 && i != totalPages && (i < totalPages / 2 - 1 || i > totalPages / 2 + 1)) {
                continue;
            }
            map.put(i,i);
        }
        LOG.info("map = " + map);
        model.addAttribute("totalPages", map);
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
    public String aaa(Model model,
//                      @RequestParam Integer newPageNum,
                      BookSearchDto bookSearchDto) {
        LOG.info("model = " + model);
        LOG.info("bookSearchDto = " + bookSearchDto);
        Page<Book> books;
        Integer pageSize = bookSearchDto.getPageSize();
        String bookName = bookSearchDto.getBookName();
        LOG.info("pageSize = " + pageSize);
        LOG.info("bookName = " + bookName);

        books = bookService.findPage(new BookSearchDto(bookName, bookSearchDto.getNewPageNum(), pageSize));
        LOG.info("books = " + books);
        model.addAttribute("books", books.getContent());
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 1; i <= books.getTotalPages(); i++) {
            map.put(i,i);
        }
        model.addAttribute("totalPages", map);
        model.addAttribute("hasPre", books.hasPrevious());
        model.addAttribute("hasNext", books.hasNext());
        model.addAttribute("pageNum", books.getNumber() + 1);
        model.addAttribute("pageSize", books.getSize());
        model.addAttribute("searchBookName", bookSearchDto.getBookName());
        return "home::bookList";
    }


    // ?????????????????????
    @PostMapping(value = "/upload1")
    @ResponseBody
    @Hidden
    public CommonResult upload1(HttpServletRequest request,
                               @RequestParam("file") MultipartFile uploadFile) throws Exception {
        if (uploadFile != null) {
            String filename = uploadFile.getOriginalFilename();
            AVFile file = new AVFile(filename, uploadFile.getBytes());
            file.saveInBackground(true).subscribe(new Observer<AVFile>() {

                @Override
                public void onSubscribe(Disposable disposable) {}

                @Override
                public void onNext(AVFile file) {
                    LOG.debug("?????????????????? objectId???" + file.getObjectId());
                }

                @Override
                public void onError(Throwable throwable) {
                    LOG.debug("failed to get data. cause: " + throwable.getMessage());
                }

                @Override
                public void onComplete() {}

            });;
            LOG.info("file.getUrl()" + file.getUrl());
            LOG.info("file" + file);
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

    // ??????????????????????????????MultipartFile???
    @PostMapping(value = "/upload")
    @ResponseBody
    @Hidden
    public CommonResult upload(HttpServletRequest request,
                               @RequestParam("file") MultipartFile uploadFile, @RequestParam("bookName") String bookName, @RequestParam("bookAuthor") String bookAuthor, @RequestParam("price") String price) throws Exception {

        // ??????????????????????????????????????????
        if (!uploadFile.isEmpty()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            //?????????????????????????????????"???????????????"--???????????????????????????????????????????????????????????????
            String realPath = new String("src/main/resources/" + filePrefix);
            LOG.info("-----------??????????????????????????????" + realPath + "???-----------");
            String format = sdf.format(new Date());
            //??????????????????????????????
            File file = new File(realPath + format);
            LOG.info("-----------?????????????????????????????????" + file + "???-----------");
            LOG.info("-----------??????????????????????????? -- ????????????????????????????????????????????????????????????????????????????????????" + file.getAbsolutePath() + "???-----------");
            if (!file.isDirectory()) {
                //?????????????????????
                file.mkdirs();
            }

            //?????????????????????  original:?????????????????????  ??????????????????????????????????????????????????????????????????
            String oldName = uploadFile.getOriginalFilename();
            LOG.info("-----------????????????????????????" + oldName + "???-----------");
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
            LOG.info("-----------?????????????????????????????????" + newName +"???-----------");
            try {
                //???????????????????????????
                File newFile = new File(file.getAbsolutePath() + File.separator + newName);
                //??????????????????????????????????????????????????????????????????????????????????????????,??????????????????????????? ??????????????????
                uploadFile.transferTo(newFile);
//                String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
                String filePath = (file.getAbsolutePath() + "/"+ newName).replace('\\','/');
                LOG.info("-----------???" + filePath + "???-----------");

                String theBookName = bookName != null ? bookName : file.getName();
                String theBookAuthor = bookAuthor != null ? bookAuthor : "EBookShop";
                float theprice = price != null ? Float.valueOf(price) : 0f;
                LOG.info("theBookName = " + bookName);
                LOG.info("theBookAuthor = " + bookAuthor);
                LOG.info("theprice = " + price);

                Book book = new Book();
                book.setBookName(theBookName);
                book.setAuthor(theBookAuthor);
                book.setOriginalPrice(theprice);
                book.setSellingPrice(theprice - 0.2f >= 0 ? theprice - 0.2f : 0);
                book.setBookUri(filePath);
                bookService.save(book);

                return  CommonResult.success(book);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //            // ??????????????????
//            String path = request.getServletContext().getRealPath("");
//            LOG.info("path = " + path);
//            // ???????????????
//            String filename = uploadFile.getOriginalFilename();
//            // ????????????????????????????????????????????????
//            String newPath = filePrefix + filename;
//            LOG.info("filePrefix" + filePrefix);
//            //????????????????????????????????????????????????
//            uploadFile.transferTo(new File(newPath));
//
//            //??????????????????????????????????????????????????????
//            String newFilename;
//            int dot = filename.lastIndexOf('.');
//            if ((dot > -1) && (dot < (filename.length()))) {
//                newFilename = filename.substring(0, dot);
//            } else {
//                newFilename = filename;
//            }
//            Book book = new Book();
//            book.setBookName(newFilename);
//            book.setBookUri(newPath);
//            bookService.save(book);
//
//            return CommonResult.success("????????????");

        }
        return CommonResult.fail(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    /**
     * ?????????????????? ??? { "url": "http://example.com/books/book1.txt" }
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
            return CommonResult.success(book.getBookUrl());
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
        // ??????????????????
        String path = book.getBookUri().replace('\\','/');
        int firstIndex = path.indexOf("/uploadFile/");
        LOG.info(path.substring(firstIndex));
        int lastIndex = path.lastIndexOf(".");
        // ??????File
        String filename = book.getBookName() + path.substring(lastIndex);
        LOG.info("path= " + path);
        LOG.info("File.separator= " + File.separator);
        LOG.info(filename);
//        File file = new File(path + File.separator + filename);
        File file = new File(path);
        // ok??????Http?????????????????? 200
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        // ????????????
        builder.contentLength(file.length());
        // application/octet-stream ??? ???????????????????????????????????????????????????
        builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // ??????URLDecoder.decode????????????????????????
        filename = URLEncoder.encode(filename, "UTF-8");
        // ??????????????????????????????????????????????????????????????????????????????????????????attachment ???????????????
        // ????????????????????????????????????????????????????????????????????????????????????
        if (userAgent.indexOf("MSIE") > 0) {
            // ?????????IE???????????????UTF-8???????????????URL????????????
            builder.header("Content-Disposition", "attachment; filename=" + filename);
        } else {
            // ???FireFox???Chrome????????????????????????????????????????????????
            // ??????filename????????????*?????????UTF-8???????????????????????????
            builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
        }
        return builder.body(FileUtils.readFileToByteArray(file));
    }

    @RequestMapping("/content/refresh")
    public String getContentRe(Model model, @RequestParam Integer PageNum, @RequestParam Integer bookId) throws IOException {

        int pagenum = PageNum;
        model.addAttribute("bookId", bookId);

        Long bookID = bookId.longValue();

        int totalPageNum = bookService.getTotalPageNum(bookID);
        HashMap<Object, Object> map = new HashMap<>();
        map.put(1,1);
        for (int i = 2; i <= totalPageNum; i++) {
            if (totalPageNum > 5 && i != 1 && i != totalPageNum && (i < totalPageNum / 2 - 1 || i > totalPageNum / 2 + 1)) {
                continue;
            }
            map.put(i,i);
        }

        String content = bookService.getbookContent(bookID, pagenum);
        Book book = bookRepository.findByBookId(bookID);
        model.addAttribute("totalPages", map);
        model.addAttribute("hasPre", pagenum > 1);
        model.addAttribute("hasNext", pagenum < totalPageNum);
        model.addAttribute("pageNum", pagenum);
//        model.addAttribute("bookId", bookId);
        model.addAttribute("content", content);
        model.addAttribute("bookName", book.getBookName());
        model.addAttribute("author", book.getAuthor());
        return "content";
    }

    @RequestMapping("/content")
    public String getContent(Model model, @RequestParam Integer bookId) throws IOException {

        int pagenum = 1;



        model.addAttribute("bookId", bookId);
        Long bookID = bookId.longValue();

        int totalPageNum = bookService.getTotalPageNum(bookID);
        HashMap<Object, Object> map = new HashMap<>();
        map.put(1,1);
        for (int i = 2; i <= totalPageNum; i++) {
            if (totalPageNum > 5 && i != 1 && i != totalPageNum && (i < totalPageNum / 2 - 1 || i > totalPageNum / 2 + 1)) {
                continue;
            }
            map.put(i,i);
        }

        String content = bookService.getbookContent(bookID, pagenum);
        Book book = bookRepository.findByBookId(bookID);
        model.addAttribute("totalPages", map);
        model.addAttribute("hasPre", false);
        model.addAttribute("hasNext", pagenum < totalPageNum);
        model.addAttribute("pageNum", pagenum);
        model.addAttribute("content", content);
        model.addAttribute("bookName", book.getBookName());
        model.addAttribute("author", book.getAuthor());
        return "content";
    }


}
