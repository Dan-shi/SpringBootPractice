package com.springboot.practice.repository;

import com.springboot.practice.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
    List<Book> findByAuthor(String author);
}
