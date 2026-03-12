package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    // ISBN으로 도서 조회
    Optional<Book> findByIsbn(String isbn);

    // 저자명으로 도서 목록 조회
    List<Book> findByAuthor(String author);
}