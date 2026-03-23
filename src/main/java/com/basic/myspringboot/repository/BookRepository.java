package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"bookDetail"})
    Optional<Book> findById(Long id);

    @EntityGraph(attributePaths = {"bookDetail"})
    Optional<Book> findByIsbn(String isbn);

    // 테스트 코드 컴파일 에러 해결을 위해 추가된 메서드
    List<Book> findByAuthor(String author);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    boolean existsByIsbn(String isbn);
}