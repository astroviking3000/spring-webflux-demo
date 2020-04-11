package com.astroviking.springwebfluxdemo.repositories;

import com.astroviking.springwebfluxdemo.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {}
