package com.personal.devetblogapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
  @GetMapping
  public ResponseEntity<?> get(){
    return  ResponseEntity.ok().body("HELLO");
  }
}
