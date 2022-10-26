package com.etrade.segment.model;

import com.etrade.segment.model.helpers.Address;
import com.etrade.segment.model.helpers.Prefers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private Address address;
    private Prefers prefers;
}
