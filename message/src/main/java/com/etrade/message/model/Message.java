package com.etrade.message.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String title;
    private String content;
    private String messageType;
    private String productId;
    private String image;
    private List<String> toUserEmails;
}
