package com.nurettinyakit.restexample.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {
    private String orderId;
    private String status;
    private LocalDateTime collectionDate;

    public OrderStatusCategory getStatusCategory() {
        boolean isClosed = List.of("COMPLETED", "CANCELLED", "UNCOLLECTED", "REJECTED")
            .contains(this.getStatus());

        if (isClosed) {
            return OrderStatusCategory.CLOSED;
        } else {
            return OrderStatusCategory.OPEN;
        }
    }
}
