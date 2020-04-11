package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Category;
import com.astroviking.springwebfluxdemo.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> create(@RequestBody Publisher<Category> categoryPublisher) {
    return categoryRepository.saveAll(categoryPublisher).then();
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
    category.setId(id);
    return categoryRepository.save(category);
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
    Category savedCategory = categoryRepository.findById(id).block();

    if (savedCategory.getDescription() != category.getDescription()) {
      savedCategory.setDescription(category.getDescription());
    }
    return categoryRepository.save(savedCategory);
  }
}
