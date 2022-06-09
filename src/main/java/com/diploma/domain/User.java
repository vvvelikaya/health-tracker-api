package com.diploma.domain;

import com.diploma.domain.enumeration.Gender;
import com.diploma.domain.enumeration.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A User.
 */
@Entity
@Table(name = "user_account")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "surname", length = 20, nullable = false)
    private String surname;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @DecimalMin(value = "30")
    @DecimalMax(value = "300")
    @Column(name = "weight")
    private Double weight;

    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;

//    @Enumerated
    @Column(name = "role")
//    @Builder.Default
    private String role;
}
