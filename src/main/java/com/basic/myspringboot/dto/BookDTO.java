package com.basic.myspringboot.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

public class BookDTO {

    @Getter @Setter
    public static class Request {
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailRequest detailRequest;
    }

    @Getter @Setter
    public static class BookDetailRequest {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    @Getter @Setter
    public static class PatchRequest {
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailPatchRequest detailRequest;
    }

    @Getter @Setter
    public static class BookDetailPatchRequest {
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }
}