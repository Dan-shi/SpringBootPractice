package com.springboot.practice.repository;

import com.springboot.practice.bean.Reader;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReaderRepository
        extends JpaRepository<Reader, String> {
    Reader findByUsername(String username);
}
