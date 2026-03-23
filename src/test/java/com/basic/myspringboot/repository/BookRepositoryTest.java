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

/**
 * @author 김어진
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false) // 테스트 후에도 DB에 데이터를 남기겠다는 설정
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        // [필수 추가] 테스트 시작 전 기존 데이터를 모두 삭제하여 중복 에러를 방지합니다.
        bookRepository.deleteAll();

        Book book1 = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425") // 이 번호가 이미 DB에 있어서 에러가 났던 것임
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
        // given: 새로운 책 정보 (기존에 없는 ISBN 사용 권장)
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
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getAuthor()).isEqualTo("홍길동");
    }

    @Test
    public void testFindByAuthor() {
        List<Book> foundBooks = bookRepository.findByAuthor("박둘리");
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.get(0).getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    public void testUpdateBook() {
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow();
        book.setPrice(32000); // 엔티티에 Setter 혹은 update 메서드 필요
        Book updatedBook = bookRepository.save(book);

        assertThat(updatedBook.getPrice()).isEqualTo(32000);
    }

    @Test
    public void testDeleteBook() {
        Book book = bookRepository.findByIsbn("9788956746432").orElseThrow();
        bookRepository.delete(book);

        Optional<Book> deletedBook = bookRepository.findByIsbn("9788956746432");
        assertThat(deletedBook).isEmpty();
    }
}