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


        Page<Book> books = bookService.findPage(bookSearchDto.getPageNum(), bookSearchDto.getPageSize(), bookSearchDto.getBookName());
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
        books = bookService.findPage(bookSearchDto.getNewPageNum(), pageSize, bookName);
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


    // 上传文件至云端
    @PostMapping(value = "/upload1")
    @ResponseBody
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
                    LOG.debug("文件保存完成 objectId：" + file.getObjectId());
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

    // 上传文件会自动绑定到MultipartFile中
    @PostMapping(value = "/upload")
    @ResponseBody
    public CommonResult upload(HttpServletRequest request,
                               @RequestParam("file") MultipartFile uploadFile, @RequestParam("bookName") String bookName, @RequestParam("bookAuthor") String bookAuthor, @RequestParam("price") String price) throws Exception {

        // 如果文件不为空，写入上传路径
        if (!uploadFile.isEmpty()) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
            //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
            String realPath = new String("src/main/resources/" + filePrefix);
            LOG.info("-----------上传文件保存的路径【" + realPath + "】-----------");
            String format = sdf.format(new Date());
            //存放上传文件的文件夹
            File file = new File(realPath + format);
            LOG.info("-----------存放上传文件的文件夹【" + file + "】-----------");
            LOG.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【" + file.getAbsolutePath() + "】-----------");
            if (!file.isDirectory()) {
                //递归生成文件夹
                file.mkdirs();
            }

            //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
            String oldName = uploadFile.getOriginalFilename();
            LOG.info("-----------文件原始的名字【" + oldName + "】-----------");
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
            LOG.info("-----------文件要保存后的新名字【" + newName +"】-----------");
            try {
                //构建真实的文件路径
                File newFile = new File(file.getAbsolutePath() + File.separator + newName);
                //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
                uploadFile.transferTo(newFile);
//                String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
                String filePath = (file.getAbsolutePath() + "/"+ newName).replace('\\','/');
                LOG.info("-----------【" + filePath + "】-----------");

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
            //            // 上传文件路径
//            String path = request.getServletContext().getRealPath("");
//            LOG.info("path = " + path);
//            // 上传文件名
//            String filename = uploadFile.getOriginalFilename();
//            // 将上传文件保存到一个目标文件当中
//            String newPath = filePrefix + filename;
//            LOG.info("filePrefix" + filePrefix);
//            //将临时文件转存到我们的指定目录下
//            uploadFile.transferTo(new File(newPath));
//
//            //获取不带后缀的文件名，并存在数据库里
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
//            return CommonResult.success("上传成功");

        }
        return CommonResult.fail(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
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
        String path = book.getBookUri().replace('\\','/');
        int firstIndex = path.indexOf("/uploadFile/");
        LOG.info(path.substring(firstIndex));
        int lastIndex = path.lastIndexOf(".");
        // 构建File
        String filename = book.getBookName() + path.substring(lastIndex);
        LOG.info("path= " + path);
        LOG.info("File.separator= " + File.separator);
        LOG.info(filename);
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
