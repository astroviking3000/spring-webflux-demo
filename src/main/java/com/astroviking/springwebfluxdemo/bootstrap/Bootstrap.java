package com.astroviking.springwebfluxdemo.bootstrap;

import com.astroviking.springwebfluxdemo.domain.Category;
import com.astroviking.springwebfluxdemo.domain.Vendor;
import com.astroviking.springwebfluxdemo.repositories.CategoryRepository;
import com.astroviking.springwebfluxdemo.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

  private final VendorRepository vendorRepository;
  private final CategoryRepository categoryRepository;

  public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
    this.vendorRepository = vendorRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (vendorRepository.count().block() == 0) loadVendors();
    if (categoryRepository.count().block() == 0) loadCategories();
  }

  private void loadCategories() {
    categoryRepository.save(Category.builder().description("Fruits").build()).block();
    categoryRepository.save(Category.builder().description("Nuts").build()).block();
    categoryRepository.save(Category.builder().description("Breads").build()).block();
    categoryRepository.save(Category.builder().description("Meats").build()).block();
    categoryRepository.save(Category.builder().description("Eggs").build()).block();

    System.out.println("Categories loaded: " + categoryRepository.count().block());
  }

  private void loadVendors() {
    Vendor vendor1 = new Vendor("1", "Steve", "Jobs");
    Vendor vendor2 = new Vendor("2", "Bill", "Gates");
    Vendor vendor3 = new Vendor("3", "Linus", "Sebastion");

    vendorRepository.save(vendor1).block();
    vendorRepository.save(vendor2).block();
    vendorRepository.save(vendor3).block();

    System.out.println("Vendors loaded: " + vendorRepository.count().block());
  }
}
