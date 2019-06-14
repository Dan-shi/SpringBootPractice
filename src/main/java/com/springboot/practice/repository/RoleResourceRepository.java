package com.springboot.practice.repository;

import com.springboot.practice.bean.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {
    List<RoleResource> findByResid(long resid);
}
