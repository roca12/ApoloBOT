package com.roca12.apolobot.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.roca12.apolobot.model.GroceryItem;


public interface GroceryItemRepository extends MongoRepository<GroceryItem, String> {

	@Query("{name:'?0'}")
	public GroceryItem findItemByName(String name);

	public ArrayList<GroceryItem> findAll();

	public Optional<GroceryItem> findById(String id);

	public void deleteById(String id);

	public long count();

}