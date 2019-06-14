package com.springboot.practice.repository;

import com.springboot.practice.bean.ReaderRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRoleRepository extends JpaRepository<ReaderRole, Long> {

    List<ReaderRole> findByUsername(String userName);
}
