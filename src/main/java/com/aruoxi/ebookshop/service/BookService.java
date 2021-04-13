package com.aruoxi.ebookshop.service;


import com.aruoxi.ebookshop.domain.Book;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Copyright(C), 2020-2021
 * FileName: BookService
 * Author: hjwforever
 * Date: 2021/4/10 0010
 * Description:
 */
public interface BookService {
   void save(Book book);
   void delete(Long id);
   Book findById(Long id);
   List<Book> findAll();
   Page<Book> findPage(Integer pageNum, Integer pageSize, String name);
}
