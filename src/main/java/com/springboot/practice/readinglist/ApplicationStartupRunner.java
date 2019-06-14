package com.springboot.practice.readinglist;

import com.springboot.practice.bean.*;
import com.springboot.practice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@Order(value=1)
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ReaderRoleRepository readerRoleRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(" application start runner");
        Reader user = new Reader();
        user.setFullname("DanDan");
        user.setPassword(new BCryptPasswordEncoder().encode("123"));
        user.setUsername("dan");

        readerRepository.save(user);

        Reader user2 = readerRepository.findByUsername("dan");
        System.out.println("user: "+user2.getPassword()+" "+ user2.getFullname());

        Role role = new Role();
        role.setRoleName("Reader");
        role.setRoleNameZh("ReaderRole");

        Role role2 = new Role();
        role2.setRoleName("Admin");
        role2.setRoleNameZh("AdminRole");

        roleRepository.save(role);
        roleRepository.save(role2);

        Resource resource = new Resource();
        resource.setResName("readinglist");
        resource.setUrl("/readinglist");
        resourceRepository.save(resource);

        Role adRole = roleRepository.findByRoleName("Admin");
        Role reRole = roleRepository.findByRoleName("Reader");
        System.out.println("11"+ adRole.getId());
        ReaderRole readerRole = new ReaderRole();
        readerRole.setRoleId(reRole.getId());
        readerRole.setUsername("dan");
        readerRoleRepository.save(readerRole);

        Resource readResource = resourceRepository.findByResName(resource.getResName());

        RoleResource roleResource = new RoleResource();
        roleResource.setResid(readResource.getId());
        roleResource.setRid(reRole.getId());
        roleResourceRepository.save(roleResource);
        List<ReaderRole> readerRoleList = readerRoleRepository.findByUsername("dan");
        List<Role> roleList = readerRoleList.stream().map(item -> roleRepository.findById(item.getRoleId()).get()).collect(Collectors.toList());
        roleList.stream().forEach( t -> System.out.println
                (t.getRoleName()));

    }
}
