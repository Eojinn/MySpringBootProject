package com.basic.myspringboot.service;

import com.basic.myspringboot.dto.BookDTO;
import com.basic.myspringboot.entity.Book;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션 적용
public class BookService {

    private final BookRepository bookRepository;

    @Transactional // 데이터 변경이 일어나는 메서드에 적용

    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException("이미 존재하는 ISBN입니다.", HttpStatus.BAD_REQUEST);
        }

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .publishDate(request.getPublishDate())
                .build();

        Book savedBook = bookRepository.save(book);
        return BookDTO.BookResponse.from(savedBook);
    }

    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 ID의 도서를 찾을 수 없습니다: " + id, HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서를 찾을 수 없습니다: " + isbn, HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 ID의 도서를 찾을 수 없습니다: " + id, HttpStatus.NOT_FOUND));

        // 요청 데이터가 null이 아닐 때만 기존 엔티티의 값을 변경
        if (request.getTitle() != null) existBook.setTitle(request.getTitle());
        if (request.getAuthor() != null) existBook.setAuthor(request.getAuthor());
        if (request.getPublishDate() != null) existBook.setPublishDate(request.getPublishDate());
        if (request.getPrice() != null) existBook.setPrice(request.getPrice());

        // JPA의 영속성 컨텍스트(Dirty Checking) 덕분에 별도의 save() 호출 없이도 트랜잭션 종료 시 자동 업데이트 됩니다.
        return BookDTO.BookResponse.from(existBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("해당 ID의 도서를 찾을 수 없습니다: " + id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }
}