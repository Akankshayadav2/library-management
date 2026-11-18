package com.akanksha.library.dto;

/**
 * DTO = Data Transfer Object.
 * This is the JSON the client SENDS when creating/updating a book:
 *   { "title": "...", "author": "...", "category": "...", "totalCopies": 3 }
 *
 * Why not accept the Book entity directly?
 *   - The client should NOT be able to set "id" or "availableCopies" itself.
 *   - Our API stays stable even if the database entity changes.
 *
 * (Validation annotations like @NotBlank are added in Phase 2.)
 */
public class BookRequest {

    private String title;
    private String author;
    private String category;
    private Integer totalCopies;

    public BookRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }
}
