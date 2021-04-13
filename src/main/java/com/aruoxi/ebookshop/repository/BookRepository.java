package com.aruoxi.ebookshop.repository;

import com.aruoxi.ebookshop.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  /**
   * 分页查找
   * @param name
   * @param pageRequest
   * @return
   */
  @Query(value = "select * from ebooks where name like %?1%", countQuery = "select count(*) from ebooks where name = %?1%",  nativeQuery = true)
  Page<Book> findByNameLike(String name, Pageable pageRequest);
}
