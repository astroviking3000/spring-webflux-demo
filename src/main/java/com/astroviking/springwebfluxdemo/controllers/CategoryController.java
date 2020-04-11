package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Category;
import com.astroviking.springwebfluxdemo.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

  public static final String BASE_URL = "/api/v1/category";
  private final CategoryRepository categoryRepository;

  public CategoryController(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @GetMapping
  public Flux<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @GetMapping("{id}")
  public Mono<Category> getById(@PathVariable String id) {
    return categoryRepository.findById(id);
  }
}
