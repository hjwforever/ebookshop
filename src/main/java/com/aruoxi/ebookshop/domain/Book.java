package com.aruoxi.ebookshop.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name = "电子书实体类")
@Validated
@Entity
@Table(name = "ebooks")
@Getter
@Setter
public class Book implements Serializable {

  private static final long SERIAL_VERSION_UID = 4048798961366546485L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bookId;

  @NotBlank
  @Size(max = 100)
  @Schema(description = "书籍名字", example = "Java从入门到如土", required = true)
  private String bookName;

  @Size(max = 100)
  @Schema(description = "书籍作者")
  private String author;

  @Size(max = 200)
  @Schema(description = "书籍介绍", example = "这是一本经典之作")
  private String bookIntro;

  @Schema(description = "书籍 存储位置/链接", example = "/books/1, http://example.com/books/1")
  private String bookUri;

  @Schema(description = "书籍封面图片", example = "http://example.com/books/1/CoverImg")
  private String bookCoverImg;

  @Schema(description = "书籍原价")
  private Float originalPrice;

  @Schema(description = "书籍现价")
  private Float sellingPrice;

  @Schema(description = "分类id")
  private Long bookCategoryId;

  @Schema(description = "书籍标签")
  private String tag;

  @Schema(description = "书籍销售状态", example = "售罄")
  private Byte bookSellStatus;

  @Schema(description = "创建时间")
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @Schema(description = "更新时间")
  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date updateTime;
}