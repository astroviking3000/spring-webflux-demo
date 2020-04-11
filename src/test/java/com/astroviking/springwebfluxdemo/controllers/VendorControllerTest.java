package com.astroviking.springwebfluxdemo.controllers;

import com.astroviking.springwebfluxdemo.domain.Vendor;
import com.astroviking.springwebfluxdemo.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.astroviking.springwebfluxdemo.controllers.VendorController.BASE_URL;
import static org.mockito.BDDMockito.given;

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
}
