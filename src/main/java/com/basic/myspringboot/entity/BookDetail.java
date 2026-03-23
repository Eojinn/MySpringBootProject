package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String language;
    private Integer pageCount;
    private String publisher;
    private String coverImageUrl;
    private String edition;

    // 관계의 주인 (외래키 소유)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

    protected void assignBook(Book book) {
        this.book = book;
    }

    // 부분 수정 로직 (PATCH)
    public void patch(String description, String language, Integer pageCount, String publisher, String coverImageUrl, String edition) {
        if (description != null) this.description = description;
        if (language != null) this.language = language;
        if (pageCount != null) this.pageCount = pageCount;
        if (publisher != null) this.publisher = publisher;
        if (coverImageUrl != null) this.coverImageUrl = coverImageUrl;
        if (edition != null) this.edition = edition;
    }
}