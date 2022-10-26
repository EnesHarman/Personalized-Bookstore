package com.etrade.segment.model;

import com.etrade.segment.model.helpers.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "segments")
public class Segment {
    @Id
    private String id;
    private String name;
    private Condition condition;
    private List<String> userIds;
    private LocalDateTime createDate;
}
