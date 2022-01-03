package com.java.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.demo.model.AllSanta;

public interface SantaRepo extends JpaRepository<AllSanta, Integer>{

}
