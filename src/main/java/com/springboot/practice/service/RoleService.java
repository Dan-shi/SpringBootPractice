package com.springboot.practice.service;

import com.springboot.practice.bean.ReaderRole;
import com.springboot.practice.bean.Role;
import com.springboot.practice.bean.RoleResource;
import com.springboot.practice.repository.ReaderRoleRepository;
import com.springboot.practice.repository.ResourceRepository;
import com.springboot.practice.repository.RoleRepository;
import com.springboot.practice.repository.RoleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ReaderRoleRepository readerRoleRepository;
    @Autowired
    private RoleResourceRepository roleResourceRepository;

    public List<Role> getRolesOfUser(String username){
        List<ReaderRole> readerRoleList = readerRoleRepository.findByUsername(username);
        List<Role> roleList = readerRoleList.stream()
                                .map(item -> roleRepository.findById(item.getRoleId()).get())
                                .collect(Collectors.toList());
        return roleList;
    }

    public List<Role> getRolesOfResource(long resid){
        List<RoleResource> roleResourcesList = roleResourceRepository.findByResid(resid);
        List<Role> roleList = roleResourcesList.stream()
                                .map(item -> roleRepository.findById(item.getRid()).get())
                                .collect(Collectors.toList());
        return roleList;
    }



}
