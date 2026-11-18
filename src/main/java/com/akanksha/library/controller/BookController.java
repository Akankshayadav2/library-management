package com.akanksha.library.controller;

import com.akanksha.library.dto.BookRequest;
import com.akanksha.library.entity.Book;
import com.akanksha.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller = the "front door" for HTTP requests about books.
 * It ONLY receives the request, calls the service, and returns the response.
 * No business logic here.
 *
 * @RestController = @Controller + @ResponseBody
 *   -> every method's return value is converted to JSON automatically (by Jackson).
 * @RequestMapping("/api/books") -> all URLs in this class start with /api/books
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET /api/books -> list all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET /api/books/search?q=java -> search by title or author
    // NOTE: declared as its own path so "search" is never confused with {id}
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(name = "q", required = false) String q) {
        return bookService.searchBooks(q);
    }

    // GET /api/books/5 -> one book (404 if not found)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // POST /api/books -> create a book. Returns 201 Created + the saved book.
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequest request) {
        Book saved = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /api/books/5 -> update a book
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        return bookService.updateBook(id, request);
    }

    // DELETE /api/books/5 -> delete. Returns 204 No Content (success, empty body).
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
