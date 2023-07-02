package com.example.AuthService.security.service;

import com.example.AuthService.dto.person.PersonDTO;
import com.example.AuthService.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {

    private WebClient webClient;

    private Mono<PersonDTO> getPersonDTOByEmail(String email){
        return webClient.get()
                .uri("/api/v1/user/{email}", email)
                .retrieve()
                .bodyToMono(PersonDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        webClient = WebClient.create("http://localhost:8082");
        return Optional.ofNullable(getPersonDTOByEmail(email).block())
                .map(PersonDetails::build)
                .orElseThrow(() -> new UsernameNotFoundException("Error! Customer not found!"));
    }
}
