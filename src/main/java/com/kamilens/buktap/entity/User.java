package com.kamilens.buktap.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @NotNull
    @NotBlank
    @NotEmpty
    @Min(7)
    @Column(name = "password")
    private String password;


    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_x_book_genre",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_genre_id", referencedColumnName = "id")
    )
    private Set<BookGenre> favGenres = new HashSet<>();

    @NotNull
    @OneToMany(fetch = FetchType.LAZY)
    private Set<UserLibraryBook> library = new HashSet<>();

}
