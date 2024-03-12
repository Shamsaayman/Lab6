package org.example.lab6.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EmployeeSystem {
    @NotNull(message = "id cannot be empty")
    @Size(min=3, message="id should be more then 2 characters")
    private String id ;
    @NotNull(message = "name cannot be empty")
    @Size(min=5, message="name should be more then 4 characters")
    @Pattern(regexp = "^[a-zA-Z]+$" , message = "name should only contain characters")
    private String name ;
    @NotNull(message = "email cannot be empty")
    @Email(message = "must be a valid email")
    private String email ;
    @NotNull(message = "age cannot be empty")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = " age must be a number")
    @Min(value = 26, message = "age must be greater than 25")
    private Integer age ;
    @Pattern(regexp = "(\\+05)[0-10]{10}" , message = "enter valid phone number")
    private Integer phoneNum ;
    @NotEmpty(message = "position cannot be empty")
    @Pattern(regexp = "^(supervisor|coordinator)$",  message = "Position must be either supervisor or coordinator")
    private String position ;
    @AssertFalse
    private Boolean onLeave;
    @NotNull(message = "employment year cannot be empty")
    @PastOrPresent
    @Min(value = 1900 , message = "must be a valid year")
    private LocalDate employmentYear;
    @NotNull(message = "annual leave cannot be empty")
    @Positive(message = "Annual leave must be a positive number")
    private Integer annualLeave;


}
