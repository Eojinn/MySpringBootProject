package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.BookDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {

    Optional<BookDetail> findByBookId(Long bookId);

    @EntityGraph(attributePaths = {"book"})
    Optional<BookDetail> findById(Long id);

    List<BookDetail> findByPublisher(String publisher);
}