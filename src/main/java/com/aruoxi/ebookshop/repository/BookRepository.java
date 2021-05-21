package com.aruoxi.ebookshop.repository;

import com.aruoxi.ebookshop.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * 按名字查找所有
     *
     * @param name
     * @return
     */
   // List<Book> findByBookName(String name);

    Page<Book> findAllByBookUriIsNotNull(Pageable pageRequest);

    Book findByBookName(String name);
    Book findByBookId(Long bookId);

    /**
     * 分页查找
     *
     * @param name
     * @param pageRequest
     * @return
     */
    @Query(value = "select * from ebooks where book_name like concat('%',?1,'%')", countQuery = "select count(*) from ebooks where book_name = concat('%',?1,'%')", nativeQuery = true)
    Page<Book> findByNameLike(String name, Pageable pageRequest);

    @Query(value = "select * from ebooks where book_name like concat('%',?1,'%')", countQuery = "select count(*) from ebooks where book_name = concat('%',?1,'%')", nativeQuery = true)
    Page<Book> findByNameLikeAndBookUriIsNotNull(String name, Pageable pageRequest);
}
