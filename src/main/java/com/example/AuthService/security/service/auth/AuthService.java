package com.example.AuthService.security.service.auth;


import com.example.AuthService.constants.RoleType;
import com.example.AuthService.dto.auth.LoginRequest;
import com.example.AuthService.dto.auth.RegisterRequest;
import com.example.AuthService.dto.person.PersonDTO;
import com.example.AuthService.exceptions.AuthException;
import com.example.AuthService.security.PersonDetails;
import com.example.AuthService.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private WebClient webClient;


    private void assertPasswordEqual(String password, String confirmPassword) {
        if (!Optional.ofNullable(password).equals(Optional.ofNullable(confirmPassword))) {
            throw new AuthException("Error! Passwords is not equals");
        }
    }

    private String buildJwtToken(PersonDetails personDetails) {
        return jwtService.generateJwtToken(personDetails);
    }

    private PersonDTO buildCustomer(RegisterRequest request) {
        return PersonDTO.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(RoleType.ROLE_USER))
                .build();
    }

    public String login(LoginRequest request){
        Authentication authentication = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return buildJwtToken(personDetails);
    }

    private Mono<PersonDTO> createPerson(RegisterRequest request){
        return webClient.post()
                .uri("/api/v1/user/editor/create", request)
                .retrieve()
                .bodyToMono(PersonDTO.class);
    }

    public String createCustomer(RegisterRequest request) {
        webClient = WebClient.create("http://localhost:8082");
        assertPasswordEqual(request.getPassword(), request.getConfirmPassword());
        return buildJwtToken(PersonDetails.build(Objects.requireNonNull(createPerson(request).block())));
    }

}
