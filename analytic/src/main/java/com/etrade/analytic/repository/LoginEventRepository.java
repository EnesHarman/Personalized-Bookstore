package com.etrade.analytic.repository;

import com.etrade.analytic.model.LoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginEventRepository extends JpaRepository<LoginEvent, Long> {
}
