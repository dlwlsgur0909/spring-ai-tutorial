package spring.ai.tutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.ai.tutorial.domain.Product;
import spring.ai.tutorial.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProduct(String productName) {
        return productRepository.findByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(productName + " 상품을 찾을 수 없습니다."));
    }

}
