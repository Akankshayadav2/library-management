package com.akanksha.library.service;

import com.akanksha.library.dto.BookRequest;
import com.akanksha.library.entity.Book;
import com.akanksha.library.exception.BadRequestException;
import com.akanksha.library.exception.ResourceNotFoundException;
import com.akanksha.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer = all BUSINESS LOGIC for books lives here.
 * The controller only receives requests and calls these methods.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /*
     * Constructor injection: Spring sees this constructor and automatically
     * passes in the BookRepository bean. (No @Autowired needed with one constructor.)
     * "final" = it can never be changed after creation -> safer and easy to test.
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        // findById returns Optional<Book>. If empty -> throw our 404 exception.
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    public List<Book> searchBooks(String query) {
        // Empty search -> just return everything
        if (query == null || query.isBlank()) {
            return bookRepository.findAll();
        }
        String q = query.trim();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q, q);
    }

    @Transactional // all DB work in this method succeeds together or is rolled back together
    public Book createBook(BookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getCategory(),
                request.getTotalCopies()  // constructor also sets availableCopies = totalCopies
        );
        return bookRepository.save(book); // INSERT; returns the book with its new id
    }

    @Transactional
    public Book updateBook(Long id, BookRequest request) {
        Book book = getBookById(id); // 404 if it does not exist

        // How many copies are currently out with members?
        int issuedCopies = book.getTotalCopies() - book.getAvailableCopies();

        // Rule: we cannot own fewer copies than are currently issued
        if (request.getTotalCopies() < issuedCopies) {
            throw new BadRequestException("totalCopies cannot be less than issued copies (" + issuedCopies + ")");
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCategory(request.getCategory());
        book.setTotalCopies(request.getTotalCopies());
        // Recalculate what is on the shelf: new total minus what is still issued
        book.setAvailableCopies(request.getTotalCopies() - issuedCopies);

        return bookRepository.save(book); // UPDATE
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id); // 404 if it does not exist
        // Phase 3 adds the rule: a book that is currently issued cannot be deleted.
        bookRepository.delete(book);
    }
}
