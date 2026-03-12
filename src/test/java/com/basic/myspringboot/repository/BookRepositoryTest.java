package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 설정을 사용하거나
// 또는 properties 파일에 ddl-auto 설정을 명시해야 함
@Rollback(false)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // 테스트 실행 전, 이미지에 있는 데이터를 DB에 미리 세팅
    @BeforeEach
    void setUp() {
        Book book1 = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book book2 = Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn("9788956746432")
                .price(35000)
                .publishDate(LocalDate.of(2025, 4, 30))
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    public void testCreateBook() {
        // given
        Book newBook = Book.builder()
                .title("모던 자바 인 액션")
                .author("라울")
                .isbn("9791162242025")
                .price(34000)
                .publishDate(LocalDate.of(2019, 8, 1))
                .build();

        // when
        Book savedBook = bookRepository.save(newBook);

        // then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("모던 자바 인 액션");
    }

    @Test
    public void testFindByIsbn() {
        // given
        String targetIsbn = "9788956746425"; // 스프링 부트 입문의 ISBN

        // when
        Optional<Book> foundBook = bookRepository.findByIsbn(targetIsbn);

        // then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(foundBook.get().getAuthor()).isEqualTo("홍길동");
    }

    @Test
    public void testFindByAuthor() {
        // given
        String targetAuthor = "박둘리";

        // when
        List<Book> foundBooks = bookRepository.findByAuthor(targetAuthor);

        // then
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.get(0).getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    public void testUpdateBook() {
        // given (이미 저장된 책을 가져옴)
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow();

        // when (가격 인상 후 저장)
        book.setPrice(32000);
        Book updatedBook = bookRepository.save(book);

        // then
        assertThat(updatedBook.getPrice()).isEqualTo(32000);
    }

    @Test
    public void testDeleteBook() {
        // given
        Book book = bookRepository.findByIsbn("9788956746432").orElseThrow(); // JPA 프로그래밍

        // when
        bookRepository.delete(book);

        // then
        Optional<Book> deletedBook = bookRepository.findByIsbn("9788956746432");
        assertThat(deletedBook).isEmpty(); // 삭제되었으므로 비어있어야 함
    }
}