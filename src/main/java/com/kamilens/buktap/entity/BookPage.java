package com.kamilens.buktap.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@Entity
@Table(name = "book_pages")
public class BookPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "page")
    private Long page;

    @NotNull
    @Column(name = "is_preview")
    private Boolean preview;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(name = "file")
    private String file;

}
