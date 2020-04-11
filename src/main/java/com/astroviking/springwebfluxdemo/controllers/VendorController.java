package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Vendor;
import com.astroviking.springwebfluxdemo.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Flux<Vendor> create(@RequestBody Publisher<Vendor> vendorPublisher) {
    return vendorRepository.saveAll(vendorPublisher);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
    vendor.setId(id);
    return vendorRepository.save(vendor);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
    Vendor savedVendor = vendorRepository.findById(id).block();
    boolean change = false;
    if (savedVendor.getFirstName() != null
        && !savedVendor.getFirstName().equals(vendor.getFirstName())) {
      savedVendor.setFirstName(vendor.getFirstName());
      change = true;
    }
    if (savedVendor.getLastName() != null
        && !savedVendor.getLastName().equals(vendor.getLastName())) {
      savedVendor.setLastName(vendor.getLastName());
      change = true;
    }

    if (change) return vendorRepository.save(savedVendor);

    return Mono.just(savedVendor);
  }
}
