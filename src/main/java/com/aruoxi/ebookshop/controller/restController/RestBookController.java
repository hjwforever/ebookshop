package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.BookSearchDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/books")
public class RestBookController {

    private static final Logger log = LoggerFactory.getLogger(RestBookController.class);
    @Resource
    private BookServiceImpl bookService;

    // 根据id查询书籍
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult<Book> findById(@PathVariable Long id) {
        return CommonResult.success(bookService.findById(id));
    }

    // 分页查询指定条件的所有书籍
    @GetMapping
    public CommonResult<Page<Book>> findAll(BookSearchDto search) {
        Integer pageNum = search.getPageNum();
        Integer pageSize = search.getPageSize();
        String bookName = search.getBookName();
        return CommonResult.success(bookService.findPage(pageNum, pageSize, bookName));
    }

    // 新增书籍
    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public CommonResult<Object> add(@RequestBody Book book) {
        bookService.save(book);
        return CommonResult.success(book);
    }

    // 修改书籍
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CommonResult<Object> update(@RequestBody Book book) {
        bookService.save(book);
        return CommonResult.success();
    }

    // 删除书籍
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }

}
