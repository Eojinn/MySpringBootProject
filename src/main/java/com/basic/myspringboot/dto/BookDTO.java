package com.basic.myspringboot.dto;

import com.basic.myspringboot.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {

    @Getter @Setter
    public static class BookCreateRequest {
        @NotBlank(message = "제목은 필수 입력값입니다.")
        private String title;

        @NotBlank(message = "저자는 필수 입력값입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수 입력값입니다.")
        private String isbn;

        @NotNull(message = "가격은 필수 입력값입니다.")
        @Positive(message = "가격은 0보다 커야 합니다.")
        private Integer price;

        @NotNull(message = "출판일자는 필수 입력값입니다.")
        private LocalDate publishDate;

        // DTO -> Entity 변환 메서드
        public Book toEntity() {
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPrice(this.price);
            book.setPublishDate(this.publishDate);
            return book;
        }
    }

    @Getter @Setter
    public static class BookUpdateRequest {
        // 수정 시 특정 필드만 보낼 수 있으므로 @NotBlank 등은 상황에 맞게 조절하거나 생략합니다.
        private String title;
        private String author;

        @Positive(message = "가격은 0보다 커야 합니다.")
        private Integer price;
        private LocalDate publishDate;
    }

    @Getter
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        // Entity -> DTO 변환 메서드
        public static BookResponse from(Book book) {
            BookResponse response = new BookResponse();
            response.id = book.getId();
            response.title = book.getTitle();
            response.author = book.getAuthor();
            response.isbn = book.getIsbn();
            response.price = book.getPrice();
            response.publishDate = book.getPublishDate();
            return response;
        }
    }
}