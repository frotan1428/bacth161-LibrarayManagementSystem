package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "Name can not be null..")
    @NotBlank(message = "Name can not be white space ")//2
    @Size(min = 4,max= 25,message = "name  '${validatedValue}' must be between : {min} and {max} ")
    private String name;


    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Email(message = "Please provide the valid email")//@.com
    @Column(nullable = false, length = 30,unique = true)
    private String email;

    private String phoneNumber;

    @Setter(AccessLevel.NONE)
    private LocalDateTime registerDate = LocalDateTime.now();


    @ManyToMany
    @JoinTable(name = "teacher_book",
    joinColumns = @JoinColumn(name = "teacher_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book>  books= new ArrayList<>();



}