package com.etrade.analytic.repository;

import com.etrade.analytic.model.ProductClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductClickEventRepository extends JpaRepository<ProductClickEvent, Long> {
}
