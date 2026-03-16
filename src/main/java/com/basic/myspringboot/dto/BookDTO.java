package com.basic.myspringboot.dto;

import com.basic.myspringboot.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

public class BookDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookCreateRequest {
        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "저자는 필수입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수입니다.")
        private String isbn;

        @NotNull(message = "출판일자는 필수입니다.")
        private LocalDate publishDate;

        @NotNull(message = "가격은 필수입니다.")
        @Positive(message = "가격은 0보다 커야 합니다.")
        private Integer price;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookUpdateRequest {
        private String title;
        private String author;
        private LocalDate publishDate;

        @Positive(message = "가격은 0보다 커야 합니다.")
        private Integer price;
    }

    @Getter
    @Builder
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        // Entity를 DTO로 변환하는 메서드
        public static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .isbn(book.getIsbn())
                    .price(book.getPrice())
                    .publishDate(book.getPublishDate())
                    .build();
        }
    }
}