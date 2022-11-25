package com.etrade.analytic.repository;

import com.etrade.analytic.model.WlMessageClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WlMessageClickRepository extends JpaRepository<WlMessageClickEvent, Long> {
}
