package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.dto.BookDto;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book is not found with id  : " + id));
    }


    public void deleteBookById(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Page<Book> getAllBooksWithPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    public void updateBookByDto(Long id, BookDto bookDto) {
        Book existBook= getBookById(id);

        //update the filed  of the exist book with new value

        existBook.setTitle(bookDto.getTitle());
        existBook.setAuthor(bookDto.getAuthor());
        existBook.setPublicationDate(bookDto.getPublicationDate());
        existBook.setAuthor(bookDto.getAuthor());
        bookRepository.save(existBook);
    }

}
