package spring.ai.tutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.ai.tutorial.domain.Order;
import spring.ai.tutorial.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

}
