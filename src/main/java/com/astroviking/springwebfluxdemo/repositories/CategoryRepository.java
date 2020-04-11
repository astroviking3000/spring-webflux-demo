package com.astroviking.springwebfluxdemo.repositories;

import com.astroviking.springwebfluxdemo.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {}
