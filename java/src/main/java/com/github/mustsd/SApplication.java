package com.github.mustsd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.UnknownHostException;

/**
 * starter
 *
 * @author liusd
 */
@Slf4j
@SpringBootApplication
public class SApplication {

  public static void main(String[] args) throws UnknownHostException {
    SpringApplication.run(SApplication.class, args);
  }
}
