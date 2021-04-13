package com.aruoxi.ebookshop.service.impl;

import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private BookRepository bookRepository;

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
        Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
        Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        return bookRepository.findByNameLike(name, pageRequest);
    }
}
