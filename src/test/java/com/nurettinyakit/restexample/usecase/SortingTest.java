package com.nurettinyakit.restexample.usecase;


import com.nurettinyakit.restexample.domain.Order;
import com.nurettinyakit.restexample.domain.OrderStatusCategory;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SortingTest {

    @Test
    void shouldSort() {
        //GIVEN
        List<Order> orders = createOrders();

        //WHEN
        Instant start = Instant.now();

        orders = sort(orders);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("It took : " + timeElapsed + "ms");



        //THEN
        assertThat(orders.get(0).getOrderId()).isEqualTo("1");
        assertThat(orders.get(1).getOrderId()).isEqualTo("2");
        assertThat(orders.get(2).getOrderId()).isEqualTo("3");
        assertThat(orders.get(3).getOrderId()).isEqualTo("4");

    }

    @Test
    void shouldSortSplitMerge() {
        //GIVEN
        List<Order> orders = createOrders();

        //WHEN
        Instant start = Instant.now();

        orders = sortSplitMerge(orders);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("It took : " + timeElapsed + "ms");

        //THEN
        assertThat(orders.get(0).getOrderId()).isEqualTo("1");
        assertThat(orders.get(1).getOrderId()).isEqualTo("2");
        assertThat(orders.get(2).getOrderId()).isEqualTo("3");
        assertThat(orders.get(3).getOrderId()).isEqualTo("4");
        assertThat(orders.get(4).getOrderId()).isEqualTo("5");
        assertThat(orders.get(5).getOrderId()).isEqualTo("6");
        assertThat(orders.get(6).getOrderId()).isEqualTo("7");
        assertThat(orders.get(7).getOrderId()).isEqualTo("8");

    }

    @Test
    void shouldSortSplitMergeImproved() {
        //GIVEN
        List<Order> orders = createOrders();

        //WHEN
        Instant start = Instant.now();

        orders = sortSplitMergeImproved(orders);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("It took : " + timeElapsed + "ms");

        //THEN
        assertThat(orders.get(0).getOrderId()).isEqualTo("1");
        assertThat(orders.get(1).getOrderId()).isEqualTo("2");
        assertThat(orders.get(2).getOrderId()).isEqualTo("3");
        assertThat(orders.get(3).getOrderId()).isEqualTo("4");
        assertThat(orders.get(4).getOrderId()).isEqualTo("5");
        assertThat(orders.get(5).getOrderId()).isEqualTo("6");
        assertThat(orders.get(6).getOrderId()).isEqualTo("7");
        assertThat(orders.get(7).getOrderId()).isEqualTo("8");

    }

    private List<Order> sort(final List<Order> orders) {
        return orders.stream()
            //Sort by status
            .sorted(
                Comparator.comparing(Order::getStatusCategory)
                    .thenComparing(Order::getCollectionDate, Comparator.nullsFirst(Comparator.naturalOrder()))
            )
            .collect(Collectors.toList());
    }

    private List<Order> sortSplitMerge(final List<Order> orders) {
        var openOrders = orders.stream().filter(order -> order.getStatusCategory().equals(OrderStatusCategory.OPEN))
            .sorted(Comparator.comparing(Order::getCollectionDate))
            .collect(Collectors.toList());

        var closedOrders = orders.stream().filter(order -> order.getStatusCategory().equals(OrderStatusCategory.CLOSED))
            .sorted(Comparator.comparing(Order::getCollectionDate).reversed())
            .collect(Collectors.toList());

        return Stream.concat(openOrders.stream(), closedOrders.stream())
            .collect(Collectors.toList());
    }

    private List<Order> sortSplitMergeImproved(final List<Order> orders) {
        var openOrders = orders.stream().filter(order -> order.getStatusCategory().equals(OrderStatusCategory.OPEN))
            .sorted(Comparator.comparing(Order::getCollectionDate));

        var closedOrders = orders.stream().filter(order -> order.getStatusCategory().equals(OrderStatusCategory.CLOSED))
            .sorted(Comparator.comparing(Order::getCollectionDate).reversed());

        return Stream.concat(openOrders, closedOrders)
            .collect(Collectors.toList());
    }

    private List<Order> createOrders() {
        final List<Order> orders = new ArrayList<>();

        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(8))
            .status("PLACED")
            .orderId("1")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(7))
            .status("NEW")
            .orderId("2")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(6))
            .status("OPEN")
            .orderId("3")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(5))
            .status("OPEN")
            .orderId("4")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(5))
            .status("CANCELLED")
            .orderId("5")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(6))
            .status("UNCOLLECTED")
            .orderId("6")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(7))
            .status("REJECTED")
            .orderId("7")
            .build());
        orders.add(Order.builder()
            .collectionDate(LocalDateTime.now().minusDays(8))
            .status("UNCOLLECTED")
            .orderId("8")
            .build());

        return orders;
    }

}
