package com.java.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.java.demo.model.Alien;


public interface AlienRepo extends CrudRepository<Alien, Integer>{

}
