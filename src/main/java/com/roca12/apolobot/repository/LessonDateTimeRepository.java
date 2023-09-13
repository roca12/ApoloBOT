package com.roca12.apolobot.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.roca12.apolobot.model.LessonDateTime;

public interface LessonDateTimeRepository extends MongoRepository<LessonDateTime, String> {

	public ArrayList<LessonDateTime> findAll();

	public Optional<LessonDateTime> findById(String id);

}