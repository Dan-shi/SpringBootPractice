package com.springboot.practice.repository;

import com.springboot.practice.bean.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Resource findByResName(String resName);

    Resource findByUrl(String url);
}
