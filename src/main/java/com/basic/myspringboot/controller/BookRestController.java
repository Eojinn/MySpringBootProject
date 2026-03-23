package com.basic.myspringboot.controller;

import com.basic.myspringboot.dto.BookDTO;
import com.basic.myspringboot.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> createBook(@RequestBody BookDTO.Request request) {
        bookService.createBook(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @RequestBody BookDTO.Request request) {
        bookService.updateBook(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchBook(@PathVariable Long id, @RequestBody BookDTO.PatchRequest request) {
        bookService.patchBook(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/detail")
    public ResponseEntity<Void> patchBookDetail(@PathVariable Long id, @RequestBody BookDTO.BookDetailPatchRequest request) {
        bookService.patchBookDetail(id, request);
        return ResponseEntity.ok().build();
    }
}