package com.kamilens.buktap.entity;

import com.kamilens.buktap.entity.enumeration.Language;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "id")
@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private BookImage image;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_x_book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_author_id", referencedColumnName = "id")
    )
    private Set<BookAuthor> authors = new HashSet<>();

    @Size(min = 1)
    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_x_book_genre",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_genre_id", referencedColumnName = "id")
    )
    private Set<BookGenre> genres = new HashSet<>();

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "published_date")
    private Date publishedDate;

    @NotNull
    @NotBlank
    @NotEmpty
    @ISBN
    @Column(name = "isbn")
    private String isbn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload_date")
    private Date uploadDate;

    @NotNull
    @Column(name = "storage_folder")
    private String storageFolder;

    @Size(min = 5)
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BookPage> pages = new HashSet<>();

}
