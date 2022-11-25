package com.etrade.analytic.repository;

import com.etrade.analytic.model.ProductLinkClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLinkClickEventRepository extends JpaRepository<ProductLinkClickEvent, Long> {
}
