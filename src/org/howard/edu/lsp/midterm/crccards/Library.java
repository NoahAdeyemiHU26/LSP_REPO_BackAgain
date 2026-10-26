package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A library that maintains a collection of {@link Book} objects and
 * coordinates borrowing and returning. At most three books may be borrowed
 * at one time.
 *
 * <p>Books are stored in a {@link HashMap} keyed by ISBN, which gives
 * constant-time lookup and makes duplicate ISBNs easy to detect.</p>
 *
 * @author Noah
 */
public class Library {

    /** Maximum number of books that may be borrowed at the same time. */
    private static final int BORROW_LIMIT = 3;

    private final Map<String, Book> booksByIsbn = new HashMap<>();

    /**
     * Creates an empty library.
     */
    public Library() {
    }

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if the book is null or a book with the
     *                                  same ISBN is already in the library
     *                                  (the original book is preserved)
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        if (booksByIsbn.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + book.getIsbn());
        }
        booksByIsbn.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching {@code Book} instance, or {@code null} if absent
     */
    public Book findBook(String isbn) {
        return booksByIsbn.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN. The state change is delegated to
     * the {@link Book}.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book has this ISBN
     * @throws IllegalStateException    if the book is already borrowed or
     *                                  three books are already borrowed
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (book.isAvailable() && getBorrowedBookCount() >= BORROW_LIMIT) {
            throw new IllegalStateException("Borrowing limit of " + BORROW_LIMIT + " reached");
        }
        book.borrowBook(); // throws IllegalStateException if already borrowed
    }

    /**
     * Returns the book with the given ISBN. The state change is delegated to
     * the {@link Book}.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book has this ISBN
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        requireBook(isbn).returnBook();
    }

    /**
     * Returns all books that are currently available.
     *
     * @return a new list of available books; empty if there are none
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : booksByIsbn.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books by the given author, including borrowed ones. The
     * comparison is exact and case-sensitive.
     *
     * @param author the author to match
     * @return a new list of matching books; empty if there are none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : booksByIsbn.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographical order,
     * regardless of borrowing status. The library itself is not changed.
     *
     * @return a new sorted list; empty if the library has no books
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> result = new ArrayList<>(booksByIsbn.values());
        result.sort(Comparator.comparing(Book::getIsbn));
        return result;
    }

    /**
     * Returns the number of books currently borrowed.
     *
     * @return the current borrowed-book count
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : booksByIsbn.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Locates a book or fails.
     *
     * @param isbn the ISBN to look up
     * @return the matching book
     * @throws IllegalArgumentException if no book has this ISBN
     */
    private Book requireBook(String isbn) {
        Book book = booksByIsbn.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN: " + isbn);
        }
        return book;
    }
}
