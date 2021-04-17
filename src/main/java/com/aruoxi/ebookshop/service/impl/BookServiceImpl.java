package com.aruoxi.ebookshop.service.impl;

import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Copyright(C), 2020-2021
 * FileName: BookService
 * Author: hjwforever
 * Date: 2021/4/10 0010
 * Description:
 */
@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Resource
    private BookRepository bookRepository;

    @Resource
    private BookService bookService;

    @Override
    public void save(Book book) {
        book.setCreateTime(new Date());
        bookRepository.save(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findPage(Integer pageNum, Integer pageSize, String name) {
        Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("starts").ascending());
        if (name != null && !name.equals("")) {
            return bookRepository.findByNameLike(name, pageRequest);
        }

        return bookRepository.findAll(pageRequest);
    }

    public Page<Book> findPage(Integer pageNum, Integer pageSize) {
        return findPage(pageNum, pageSize, "");
    }

    public String getbookContent(Long bookId, int pageNum) throws IOException {
       // String filePath="E:/1.txt";
//        String filePath=bookService.findById(bookId).getBookUri();

//        String filePath = bookRepository.findByBookId(bookId).getBookUri();
//        FileInputStream fin = new FileInputStream(filePath);
//        InputStreamReader reader = new InputStreamReader(fin, "gbk");
//        BufferedReader buffReader = new BufferedReader(reader);
//        String strTmp = "";
//
//        StringBuilder bookContent = new StringBuilder();
//        int i = 0;
//        while (((strTmp = buffReader.readLine()) != null) && (i < (pageNum)*20)) {
//            System.out.println(strTmp);
//            if(i>(pageNum-1)*20){
//                bookContent.append(strTmp);
//            }
//            i++;
//        }
//        buffReader.close();
//        return bookContent.toString();

        String filePath = bookRepository.findByBookId(bookId).getBookUri();
        File textFile = new File(filePath);
        int bytes = (int) textFile.length();
//        System.out.println(bytes);
        byte[] content = new byte[(int) textFile.length()];
        try (FileInputStream fileInputStream = new FileInputStream(textFile)) {
            fileInputStream.read(content);
//            System.out.println(new String(content, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contents = new String(content, "utf-8");
        System.out.println("contents = " + contents);

        int PAGE_BYTES = 1024*3;

        int num = contents.length();
        int pages = num/PAGE_BYTES + 1;

        String contxt = "";

        if (pageNum == pages) {
            contxt = contents.substring((pageNum - 1) * PAGE_BYTES, num);
        } else {
            contxt = contents.substring((pageNum - 1) * PAGE_BYTES, pageNum * PAGE_BYTES);
        }
        return contxt;
    }

    public int getTotalPageNum(Long bookId) throws IOException {
//        String filePath="E:/1.txt";
//        String filePath=bookService.findById(bookId).getBookUri();
//        String filePath = bookRepository.findByBookId(bookId).getBookUri();
//        System.out.println(filePath);
//        FileInputStream fin = new FileInputStream(filePath);
//        InputStreamReader reader = new InputStreamReader(fin);
//        BufferedReader buffReader = new BufferedReader(reader);
//        String strTmp = "";
//
//        StringBuilder bookContent = new StringBuilder();
//        int TpageNum = 0;
//        while (((strTmp = buffReader.readLine()) != null)) {
//            TpageNum++;
//        }
//        buffReader.close();

        String filePath = bookRepository.findByBookId(bookId).getBookUri();
        File textFile = new File(filePath);
        int bytes = (int) textFile.length();
//        System.out.println(bytes);
        byte[] content = new byte[(int) textFile.length()];
        try (FileInputStream fileInputStream = new FileInputStream(textFile)) {
            fileInputStream.read(content);
//            System.out.println(new String(content, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contents = new String(content, "utf-8");
        System.out.println("contents = " + contents);

        int PAGE_BYTES = 1024*3;

        int num = contents.length();
        int pages = num/PAGE_BYTES + 1;

        return pages;
    }
}
