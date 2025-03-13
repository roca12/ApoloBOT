package com.roca12.apolobot.repository;

import com.roca12.apolobot.model.ReRunApolo;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReRunApoloRepository extends MongoRepository<ReRunApolo, String> {

  public ArrayList<ReRunApolo> findAll();

  public Optional<ReRunApolo> findById(String id);

  public long count();
}
