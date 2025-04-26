package com.taron.suppliers.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orders", path = "/orders")
public interface OrdersProxy {
    @GetMapping("/existsBySupplier")
    boolean existsBySupplierId(@RequestParam int supplierId);
}

