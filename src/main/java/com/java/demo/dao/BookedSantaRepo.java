package com.java.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.demo.model.BookedSanta;

public interface BookedSantaRepo extends JpaRepository<BookedSanta, Integer>{

}
