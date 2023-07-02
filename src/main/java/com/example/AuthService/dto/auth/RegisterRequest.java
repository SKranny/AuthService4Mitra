package com.example.AuthService.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Регистрация")
public class RegisterRequest {
    @Email
    @NotBlank
    @Schema(description = "E-mail")
    private String email;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Schema(description = "Пароль")
    @JsonProperty(value = "password1", required = true)
    private String password;

    @Schema(description = "Подтверждение пароля")
    @JsonProperty(value = "password2", required = true)
    private String confirmPassword;
}
