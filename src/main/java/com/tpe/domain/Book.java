package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Title  can not be null..")
    @NotBlank(message = "Title  can not be white space ")//2
    @Size(min = 4,max= 25,message = "Title  '${validatedValue}' must be between : {min} and {max} ")
    private String title;


    private String author;

    @Column(nullable = false)
    private String publicationDate;

    @ManyToMany(mappedBy = "books",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JsonIgnoreProperties("books")
    private List<Teacher> teachers= new ArrayList<>();



}
