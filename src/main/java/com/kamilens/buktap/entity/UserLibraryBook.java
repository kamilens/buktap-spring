package com.kamilens.buktap.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(of = "id")
@Data
@Entity
@Table(name = "user_library_books")
public class UserLibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Lob
    @Column(name = "comment")
    private String comment;

    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "rating")
    private Short rating;

}
