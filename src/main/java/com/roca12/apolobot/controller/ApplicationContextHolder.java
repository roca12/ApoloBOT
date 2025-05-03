package com.roca12.apolobot.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Utility class that holds the Spring ApplicationContext. This class implements
 * ApplicationContextAware to get access to the Spring context, allowing non-Spring-managed classes
 * to access Spring beans.
 *
 * @author roca12
 * @version 2025-I
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
  /** Static reference to the Spring ApplicationContext */
  private static ApplicationContext context;

  /**
   * Sets the ApplicationContext from Spring. This method is called automatically by Spring during
   * application startup.
   *
   * @param applicationContext The Spring application context
   * @throws BeansException If there is an error accessing the beans
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  /**
   * Gets the ApplicationContext.
   *
   * @return The Spring application context
   */
  public static ApplicationContext getContext() {
    return context;
  }
}
