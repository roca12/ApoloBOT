package com.roca12.apolobot.service;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.ReRunApolo;
import com.roca12.apolobot.repository.ReRunApoloRepository;
import java.util.Date;
import org.springframework.stereotype.Controller;

@Controller
public class ReRunApoloService {

  private ReRunApoloRepository reRunRepo;

  public ReRunApoloService() {
    reRunRepo = ApplicationContextHolder.getContext().getBean(ReRunApoloRepository.class);
  }

  public void createNewReRun() {
    reRunRepo.save(new ReRunApolo(null, new Date(), "Re run"));
  }

  public void createNewReRunByTest() {
    reRunRepo.save(new ReRunApolo(null, new Date(), "Test"));
  }

  public void showAllReRuns() {
    reRunRepo.findAll().forEach(item -> System.out.println(item.toString()));
  }

  public void ReRuns() {
    long count = reRunRepo.count();
    System.out.println("Number of documents in the collection: " + count);
  }
}
