package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Vendor;
import com.astroviking.springwebfluxdemo.repositories.VendorRepository;
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

import static com.astroviking.springwebfluxdemo.controllers.VendorController.BASE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class VendorControllerTest {

  public static final String ID = "test_id";
  @Mock VendorRepository vendorRepository;

  @InjectMocks VendorController vendorController;

  WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(vendorController).build();
  }

  @Test
  void findAll() {
    given(vendorRepository.findAll()).willReturn(Flux.just(new Vendor(), new Vendor()));

    webTestClient.get().uri(BASE_URL).exchange().expectBodyList(Vendor.class).hasSize(2);
  }

  @Test
  void findById() {
    given(vendorRepository.findById(ID)).willReturn(Mono.just(new Vendor()));

    webTestClient.get().uri(BASE_URL + "/" + ID).exchange().expectBody(Vendor.class);
  }

  @Test
  void testCreate() {
    given(vendorRepository.saveAll(any(Publisher.class)))
        .willReturn(Flux.just(Vendor.builder().build()));

    Mono<Vendor> vendorMono =
        Mono.just(Vendor.builder().firstName("Jim").lastName("Jones").build());

    webTestClient
        .post()
        .uri(BASE_URL)
        .body(vendorMono, Vendor.class)
        .exchange()
        .expectStatus()
        .isCreated();
  }

  @Test
  void testUpdate() {
    given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));

    Mono<Vendor> vendorMono =
        Mono.just(Vendor.builder().firstName("Jim").lastName("Jones").build());

    webTestClient
        .put()
        .uri(BASE_URL + "/" + ID)
        .body(vendorMono, Vendor.class)
        .exchange()
        .expectStatus()
        .isAccepted();
  }

  @Test
  void testPatch() {
    given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().build()));
    given(vendorRepository.save(any(Vendor.class)))
        .willReturn(Mono.just(Vendor.builder().firstName("Jim").build()));

    Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Bob").build());

    webTestClient
        .patch()
        .uri(BASE_URL + "/" + ID)
        .body(vendorMono, Vendor.class)
        .exchange()
        .expectStatus()
        .isAccepted();

    verify(vendorRepository, times(1)).findById(ID);
    verify(vendorRepository, times(1)).save(any(Vendor.class));
  }

  @Test
  void testPatchNoUpdate() {
    given(vendorRepository.findById(anyString()))
        .willReturn(Mono.just(Vendor.builder().firstName("Jim").build()));
    given(vendorRepository.save(any(Vendor.class)))
        .willReturn(Mono.just(Vendor.builder().firstName("Jim").build()));

    Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Jim").build());

    webTestClient
        .patch()
        .uri(BASE_URL + "/" + ID)
        .body(vendorMono, Vendor.class)
        .exchange()
        .expectStatus()
        .isAccepted();

    verify(vendorRepository, times(1)).findById(ID);
    verify(vendorRepository, never()).save(any(Vendor.class));
  }
}
