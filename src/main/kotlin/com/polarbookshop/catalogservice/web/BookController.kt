package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.invoke.MethodHandles

@RestController
@RequestMapping("books")
class BookController(bookService: BookService) {

    companion object {

        private val log: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }

    private val bookService: BookService = bookService

    @GetMapping
    fun get(): Iterable<Book> {
        log.info("Fetching the list of books in the catalog")
        return bookService.viewBookList()
    }

    @GetMapping("{isbn}")
    fun getByIsbn(@PathVariable isbn: String): Book {
        log.info("Fetching the book with ISBN {} from the catalog", isbn)
        return bookService.viewBookDetails(isbn)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@Valid @RequestBody book: Book): Book {
        log.info("Adding a new book to the catalog with ISBN {}", book.isbn)
        return bookService.addBookToCatalog(book)
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable isbn: String) {
        log.info("Deleting book with ISBN {}", isbn)
        bookService.removeBookFromCatalog(isbn)
    }

    @PutMapping("{isbn}")
    fun put(@PathVariable isbn: String, @Valid @RequestBody book: Book): Book {
        log.info("Updating book with ISBN {}", isbn)
        return bookService.editBookDetails(isbn, book)
    }
}
