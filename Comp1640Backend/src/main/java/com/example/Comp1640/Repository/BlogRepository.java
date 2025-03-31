package com.example.Comp1640.Repository;

import com.example.Comp1640.Entity.Admin;
import com.example.Comp1640.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {



}
