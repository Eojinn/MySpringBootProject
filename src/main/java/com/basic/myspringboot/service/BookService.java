package com.basic.myspringboot.service;

import com.basic.myspringboot.dto.BookDTO;
import com.basic.myspringboot.entity.Book;
import com.basic.myspringboot.entity.BookDetail;
import com.basic.myspringboot.repository.BookDetailRepository;
import com.basic.myspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookDetailRepository bookDetailRepository;

    @Transactional
    public Long createBook(BookDTO.Request request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + request.getIsbn());
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        BookDTO.BookDetailRequest detailReq = request.getDetailRequest();
        if (detailReq != null) {
            BookDetail bookDetail = BookDetail.builder()
                    .description(detailReq.getDescription())
                    .language(detailReq.getLanguage())
                    .pageCount(detailReq.getPageCount())
                    .publisher(detailReq.getPublisher())
                    .coverImageUrl(detailReq.getCoverImageUrl())
                    .edition(detailReq.getEdition())
                    .build();
            book.setBookDetail(bookDetail);
        }

        return bookRepository.save(book).getId();
    }

    @Transactional
    public void updateBook(Long id, BookDTO.Request request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + request.getIsbn());
        }

        book.update(request.getTitle(), request.getAuthor(), request.getIsbn(), request.getPrice(), request.getPublishDate());
    }

    @Transactional
    public void patchBook(Long id, BookDTO.PatchRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (request.getIsbn() != null && !book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + request.getIsbn());
        }

        book.patch(request.getTitle(), request.getAuthor(), request.getIsbn(), request.getPrice(), request.getPublishDate());

        if (request.getDetailRequest() != null && book.getBookDetail() != null) {
            BookDTO.BookDetailPatchRequest detailReq = request.getDetailRequest();
            book.getBookDetail().patch(detailReq.getDescription(), detailReq.getLanguage(),
                    detailReq.getPageCount(), detailReq.getPublisher(),
                    detailReq.getCoverImageUrl(), detailReq.getEdition());
        }
    }

    @Transactional
    public void patchBookDetail(Long bookId, BookDTO.BookDetailPatchRequest request) {
        BookDetail bookDetail = bookDetailRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("BookDetail not found"));

        bookDetail.patch(request.getDescription(), request.getLanguage(),
                request.getPageCount(), request.getPublisher(),
                request.getCoverImageUrl(), request.getEdition());
    }
}