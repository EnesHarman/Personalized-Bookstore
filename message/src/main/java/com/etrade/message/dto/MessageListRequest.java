package com.etrade.message.dto;

import com.etrade.message.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageListRequest {
    private String id;
    private String title;
    private String content;
    private String messageType;
    private String productId;
    private String image;
}
