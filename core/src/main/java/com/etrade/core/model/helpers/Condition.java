package com.etrade.core.model.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Condition {
    private LocalDate baseBirthDate;
    private LocalDate topBirthDate;
    private String gender;
    private String city;
    private String country;
    private String prefer;
}
