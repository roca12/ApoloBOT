package com.roca12.apolobot.service;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.ReRunApolo;
import com.roca12.apolobot.repository.ReRunApoloRepository;
import java.util.Date;
import org.springframework.stereotype.Controller;

/**
 * Service class for managing ReRunApolo entities. This class provides methods to create, retrieve,
 * and count ReRunApolo records, which track bot restart events.
 *
 * @author roca12
 * @version 2025-I
 */
@Controller
public class ReRunApoloService {

  /** Repository for accessing ReRunApolo data */
  private ReRunApoloRepository reRunRepo;

  /**
   * Default constructor. Initializes the repository by getting it from the Spring application
   * context.
   */
  public ReRunApoloService() {
    reRunRepo = ApplicationContextHolder.getContext().getBean(ReRunApoloRepository.class);
  }

  /**
   * Creates a new ReRunApolo record with the current date and "Re run" as the kind. This method is
   * typically called when the bot is restarted normally.
   */
  public void createNewReRun() {
    reRunRepo.save(new ReRunApolo(null, new Date(), "Re run"));
  }

  /**
   * Creates a new ReRunApolo record with the current date and "Test" as the kind. This method is
   * typically called when the bot is restarted for testing purposes.
   */
  public void createNewReRunByTest() {
    reRunRepo.save(new ReRunApolo(null, new Date(), "Test"));
  }

  /**
   * Displays all ReRunApolo records in the console. This method retrieves all records from the
   * repository and prints them.
   */
  public void showAllReRuns() {
    reRunRepo.findAll().forEach(item -> System.out.println(item.toString()));
  }

  /**
   * Counts and displays the number of ReRunApolo records in the console. This method prints the
   * total count of records in the repository.
   */
  public void ReRuns() {
    long count = reRunRepo.count();
    System.out.println("Number of documents in the collection: " + count);
  }
}
