package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Book; // 중요: java.awt.print.Book이 아님을 확인하세요!
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        // Given: 이미지의 첫 번째 데이터 활용
        Book book = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .publishDate(LocalDate.of(2025, 5, 7))
                .price(30000)
                .build();

        // When
        Book savedBook = bookRepository.save(book);

        // Then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        // Given: 이미지의 두 번째 데이터 등록
        String targetIsbn = "9788956746432";
        bookRepository.save(Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn(targetIsbn)
                .publishDate(LocalDate.of(2025, 4, 30))
                .price(35000)
                .build());

        // When
        Optional<Book> foundBook = bookRepository.findByIsbn(targetIsbn);

        // Then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        // Given
        String authorName = "홍길동";
        bookRepository.save(Book.builder().title("스프링 부트 입문").author(authorName).isbn("9788956746425").build());

        // When
        List<Book> books = bookRepository.findByAuthor(authorName);

        // Then
        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getAuthor()).isEqualTo(authorName);
    }

    @Test
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        // Given
        Book book = bookRepository.save(Book.builder().title("기존 제목").author("작가").isbn("0000").price(1000).build());

        // When
        book.setTitle("수정된 제목");
        book.setPrice(50000);
        Book updatedBook = bookRepository.save(book);

        // Then
        assertThat(updatedBook.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedBook.getPrice()).isEqualTo(50000);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        // Given
        Book book = bookRepository.save(Book.builder().title("삭제할 책").author("작가").isbn("9999").build());

        // When
        bookRepository.delete(book);
        Optional<Book> deletedBook = bookRepository.findById(book.getId());

        // Then
        assertThat(deletedBook).isEmpty();
    }
}