package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Vendor;
import com.astroviking.springwebfluxdemo.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

  public static final String BASE_URL = "/api/v1/vendor";
  VendorRepository vendorRepository;

  public VendorController(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  @GetMapping
  public Flux<Vendor> findAll() {
    return vendorRepository.findAll();
  }

  @GetMapping("{id}")
  public Mono<Vendor> findById(@PathVariable String id) {
    return vendorRepository.findById(id);
  }
}
