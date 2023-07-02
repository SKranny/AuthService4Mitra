package com.example.AuthService.security;

import com.example.AuthService.constants.RoleType;
import com.example.AuthService.dto.person.PersonDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PersonDetails implements UserDetails {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Builder.Default
    private boolean enabled = true;

    @Builder.Default
    private boolean accountNonLocked = true;

    private List<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return lastName + " " + firstName;
    }
    public static PersonDetails build(PersonDTO personDTO) {
        return PersonDetails.builder()
                .id(personDTO.getId())
                .email(personDTO.getEmail())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .password(personDTO.getPassword())
                .authorities(personDTO.getRoles().stream()
                        .map(RoleType::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build();
    }
}
