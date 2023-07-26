package com.tpe.controller;

import com.tpe.domain.Book;
import com.tpe.domain.Teacher;
import com.tpe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")//http://localhost:8080/books
public class BookController {

    @Autowired
    private BookService bookService;

    /*
    {
    "title": "Sample Book",
    "author": "John Doe",
    "publicationDate": "2023-07-20"
   }
     */

    @PostMapping//http://localhost:8080/books
    public ResponseEntity<Map<String, String>> saveBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Book has been saved successfully.");
        map.put("success", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); // HttpStatus: 201 (CREATED)
    }

    @GetMapping//http://localhost:8080/books
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);

    }

    @GetMapping("/{id}")//http://localhost:8080/books/1
    public ResponseEntity<Book> getBookByIdWithPathVariable(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")//http://localhost:8080/books/1
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book with ID " + id + " has been deleted.", HttpStatus.OK);
    }





}
