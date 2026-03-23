package com.basic.myspringboot.entity;

import com.basic.myspringboot.entity.BookDetail;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(unique = true)
    private String isbn;

    private Integer price;
    private LocalDate publishDate;

    // 양방향 1:1 매핑. Book은 관계의 주인이 아님
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookDetail bookDetail;

    public void setBookDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
        if (bookDetail != null) {
            bookDetail.assignBook(this);
        }
    }

    // 전체 수정 로직 (PUT)
    public void update(String title, String author, String isbn, Integer price, LocalDate publishDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publishDate = publishDate;
    }

    // 부분 수정 로직 (PATCH)
    public void patch(String title, String author, String isbn, Integer price, LocalDate publishDate) {
        if (title != null) this.title = title;
        if (author != null) this.author = author;
        if (isbn != null) this.isbn = isbn;
        if (price != null) this.price = price;
        if (publishDate != null) this.publishDate = publishDate;
    }

    // 테스트를 위한 Setter (필요시 사용)
    public void setPrice(Integer price) {
        this.price = price;
    }
}