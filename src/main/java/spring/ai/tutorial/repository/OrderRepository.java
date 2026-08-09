package spring.ai.tutorial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.ai.tutorial.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(
        """
        SELECT o
        FROM Order o
        JOIN FETCH o.customer c
        JOIN FETCH o.orderItems oi
        JOIN FETCH oi.product p
        WHERE c.name = :customerName
        """
    )
    List<Order> findByCustomerName(String customerName);

}
