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
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    public List<BookDTO.BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    public BookDTO.BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("도서를 찾을 수 없습니다. ID: " + id, HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        return BookDTO.BookResponse.from(book);
    }

    public List<BookDTO.BookResponse> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        // ISBN 중복 검사
        if (bookRepository.findByIsbn(request.getIsbn()).isPresent()) {
            throw new BusinessException("이미 존재하는 ISBN 입니다.", HttpStatus.CONFLICT);
        }
        Book savedBook = bookRepository.save(request.toEntity());
        return BookDTO.BookResponse.from(savedBook);
    }

    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("도서를 찾을 수 없습니다. ID: " + id, HttpStatus.NOT_FOUND));

        // 변경이 필요한 필드만 업데이트 (null 체크)
        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        }
        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }

        // JPA의 Dirty Checking에 의해 트랜잭션 종료 시 자동 업데이트 쿼리 발생
        return BookDTO.BookResponse.from(existBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("도서를 찾을 수 없습니다. ID: " + id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }
}