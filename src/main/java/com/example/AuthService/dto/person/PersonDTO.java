package com.example.AuthService.dto.person;

import com.example.AuthService.constants.RoleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    @Email
    @JsonProperty(required = true)
    private String email;

    private LocalDate birthDay;
    @NotNull
    @JsonProperty(required = true)
    private Set<RoleType> roles;
    @NotNull
    @JsonProperty(required = true)
    private String password;
}
