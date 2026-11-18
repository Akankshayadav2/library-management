package com.akanksha.library.repository;

import com.akanksha.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository = the layer that talks to the database.
 *
 * We only write an INTERFACE. Spring Data JPA creates the implementation at runtime.
 * JpaRepository<Book, Long> gives us for free:
 *   findAll(), findById(id), save(book), deleteById(id), count(), existsById(id) ...
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /*
     * "Derived query": Spring reads the METHOD NAME and builds the SQL for us:
     *   SELECT * FROM books
     *   WHERE LOWER(title) LIKE LOWER('%q%') OR LOWER(author) LIKE LOWER('%q%')
     * We pass the same search text twice (once for title, once for author).
     */
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}
