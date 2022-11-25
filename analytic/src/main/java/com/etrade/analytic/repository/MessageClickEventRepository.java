package com.etrade.analytic.repository;

import com.etrade.analytic.model.MessageClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageClickEventRepository extends JpaRepository<MessageClickEvent, Long> {
}
