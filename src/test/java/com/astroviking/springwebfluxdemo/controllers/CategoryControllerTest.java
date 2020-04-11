package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Category;
import com.astroviking.springwebfluxdemo.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.astroviking.springwebfluxdemo.controllers.CategoryController.BASE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

  public static final String ID = "test_id";
  public static final String DESCRIPTION = "test description";
  WebTestClient webTestClient;

  @Mock CategoryRepository categoryRepository;
  @InjectMocks CategoryController categoryController;

  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(categoryController).build();
  }

  @Test
  void getAllCategories() {
    given(categoryRepository.findAll())
        .willReturn(
            Flux.just(
                Category.builder().description("Cat1").build(),
                Category.builder().description("Cat1").build()));

    webTestClient.get().uri(BASE_URL).exchange().expectBodyList(Category.class).hasSize(2);
  }

  @Test
  void getById() {
    given(categoryRepository.findById(ID))
        .willReturn(Mono.just(Category.builder().id(ID).description(DESCRIPTION).build()));

    webTestClient.get().uri(BASE_URL + "/" + ID).exchange().expectBody(Category.class);
  }

  @Test
  void testCreate() {
    given(categoryRepository.saveAll(any(Publisher.class)))
        .willReturn(Flux.just(Category.builder().build()));

    Mono<Category> categoryToSave = Mono.just(Category.builder().description(DESCRIPTION).build());

    webTestClient
        .post()
        .uri(BASE_URL)
        .body(categoryToSave, Category.class)
        .exchange()
        .expectStatus()
        .isCreated();
  }
}
