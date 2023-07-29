package com.tpe.service;

import com.tpe.domain.Book;
import com.tpe.domain.Teacher;
import com.tpe.dto.BookDto;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TeacherService teacherService;

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


    @Transactional
    public ResponseEntity<Map<String, String>> addBookForTeacher(Long teacherId, Long bookId) {

        //step 1: find teacher by id
        Teacher teacher= teacherService.getTeacherById(teacherId);
        //find book By id

        Optional<Book>  optionalBook = bookRepository.findById(bookId);//optional(null)
        if (optionalBook.isEmpty()){
            Map<String,String> response= new HashMap<>();
            response.put("message" ,"Book with id "+bookId+" does not exist");
            response.put("success","false");

            return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);//404 (NOT FOUND)
        }

       Book book = optionalBook.get();

        //step 3 check if the book already exist for th Teacher  t =1 book =1

        boolean bookExist =  teacher.getBooks().stream().anyMatch(b->
                b.getId().equals(book.getId()));
        if (bookExist){

            Map<String,String> response= new HashMap<>();
            response.put("message" ,"Book already for the teacher with   id "+teacherId);
            response.put("success","false");

            return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);//400 (BAD _REQUEST)

        }

        //add the book to the teachers return new response
        teacher.getBooks().add(book);


        Map<String,String> response= new HashMap<>();
        response.put("message" ,"Book with id "+ bookId+ " has been added for the Teacher with id : " +teacherId);
        response.put("success","true");

        return  new ResponseEntity<>(response, HttpStatus.CREATED);//201 (CREATED)

    }

    public List<Book> getBookByAuthorUsingJPQL(String author) {
        List<Book> books = bookRepository.findByAuthorUsingJPQL(author);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found with author: " + author);
        }
        return books;
    }


}
