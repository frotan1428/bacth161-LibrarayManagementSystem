package com.tpe.repository;

import com.tpe.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitle(String title);
    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthorUsingJPQL(@Param("author") String author);

    @Query(value = "SELECT * FROM tbl_book b WHERE b.title = :bTitle", nativeQuery = true)
    List<Book> findBooksByTitleWithSQL(@Param("bTitle") String title);
}
