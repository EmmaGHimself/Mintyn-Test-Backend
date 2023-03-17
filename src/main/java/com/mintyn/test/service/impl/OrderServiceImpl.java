package com.mintyn.test.service.impl;

import com.mintyn.test.dto.OrderDto;
import com.mintyn.test.exception.NotEnoughQuantityException;
import com.mintyn.test.exception.ResourceNotFoundException;
import com.mintyn.test.model.Order;
import com.mintyn.test.model.OrderReport;
import com.mintyn.test.model.Product;
import com.mintyn.test.repository.OrderRepository;
import com.mintyn.test.repository.ProductRepository;
import com.mintyn.test.service.OrderService;
import com.mintyn.test.validation.OrderDtoValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceImpl productService;
    private final ProductRepository productRepository;
    private final OrderDtoValidator orderDtoValidator;
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String kafkaTopic = "order-topic";

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            ProductServiceImpl productService,
            OrderDtoValidator orderDtoValidator, KafkaTemplate<String, Order> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.orderDtoValidator = orderDtoValidator;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = this.orderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return new OrderDto(order);
    }

    @Override
    public List<OrderDto> findAll() {
        return this.orderRepository
                .findAll()
                .stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @KafkaListener(topics = kafkaTopic, groupId = "report_consumer")
    public OrderDto save(OrderDto orderDto) {
        this.orderDtoValidator.validate(orderDto);
        Long productId = orderDto.getProductId();
        Product product = this.productRepository.findById(productId).orElse(null);
        assert product != null;
        if (product.getStock() < orderDto.getQuantity()) {
            throw new NotEnoughQuantityException("Product with id " + orderDto.getProductId() + " does not have enough stock");
        }
        orderDto.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(orderDto.getQuantity())));
        this.productService.updateProductQuantity(orderDto.getProductId(), orderDto.getQuantity());

        Order order = this.dtoToEntity(orderDto);

        // subscribe order to Kafka
        kafkaTemplate.send(kafkaTopic, order);
        return new OrderDto(this.orderRepository.save(order));
    }

    @Override
    public void deleteById(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public List<OrderReport> getOrderReport(LocalDate startDate, LocalDate endDate) {
        return this.orderRepository.getOrderReport(startDate, endDate);
    }

    private Order dtoToEntity(OrderDto orderDto) {
        Order order = new Order();

        BeanUtils.copyProperties(orderDto, order, "orderDetails");

        Long productId = orderDto.getProductId();
        Product product = this.productRepository
                .findById(productId)
                .orElse(null);
        order.setProduct(product);

        return order;
    }
}
