package spring.ai.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.ai.tutorial.domain.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

}
