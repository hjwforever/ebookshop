package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.BookSearchDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;

import static com.aruoxi.ebookshop.controller.BookController.getBookUrl;

@Tag(name = "书籍API接口")
@RestController
@RequestMapping("/api/books")
public class RestBookController {

    private static final Logger log = LoggerFactory.getLogger(RestBookController.class);
    @Resource
    private BookServiceImpl bookService;

    @Resource
    private BookRepository bookRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(summary = "根据id查询书籍", description = "根据id查询书籍")
    public CommonResult<Book> findById(@PathVariable  @Parameter(description = "书籍id") Long id) {
        return CommonResult.success(bookService.findById(id));
    }

    // 分页查询指定条件的所有书籍
    @GetMapping
    @Operation(summary = "分页书籍列表", description = "根据BookSearchDto查询书籍")
    public CommonResult<Page<Book>> findAll(BookSearchDto search) {
        Integer pageNum = search.getPageNum();
        Integer pageSize = search.getPageSize();
        String bookName = search.getBookName();
        return CommonResult.success(bookService.findPage(pageNum, pageSize, bookName));
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    @Operation(summary = "保存书籍的实体",
        description = "上传新增书籍",
        security = @SecurityRequirement(name = "需要admin权限"))
    public CommonResult<Object> add(@RequestBody Book book) {
        bookService.save(book);
        return CommonResult.success(book);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "修改书籍",
        description = "修改书籍",
        security = @SecurityRequirement(name = "需要admin权限"))
    public CommonResult<Object> update(@RequestBody Book book) {
        bookService.save(book);
        return CommonResult.success();
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除书籍",
        description = "根据id删除书籍",
        security = @SecurityRequirement(name = "需要admin权限"))
    public void delete(@PathVariable("id") Long id) {
        bookService.delete(id);
    }
}
