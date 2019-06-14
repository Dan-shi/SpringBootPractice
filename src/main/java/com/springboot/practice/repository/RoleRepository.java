package com.springboot.practice.repository;

import com.springboot.practice.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    //根据resource的主键查找resource允许的所有权限
    @Query(value = "select r.* from Role r, RoleResource rr where rr.res_id = ?1 and rr.rid = r.id", nativeQuery = true)
    public List<Role> findRolesOfResource(long resourceId);

    Role findByRoleName(String roleName);
}
