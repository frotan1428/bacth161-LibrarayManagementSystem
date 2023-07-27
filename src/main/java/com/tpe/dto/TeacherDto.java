package com.tpe.dto;

import com.tpe.domain.Teacher;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TeacherDto {

    private Long id;

    @NotNull(message = "Name can not be null..")
    @NotBlank(message = "Name can not be white space ")//2
    @Size(min = 4,max= 25,message = "name  '${validatedValue}' must be between : {min} and {max} ")
    private String name;


    private String lastName;

    @Email(message = "Please provide the valid email")//@.com
    private String email;

    private String phoneNumber;


    private LocalDateTime registerDate = LocalDateTime.now();


    public TeacherDto(Teacher teacher) {

        this.id=teacher.getId();
        this.name=teacher.getName();
        this.lastName=teacher.getLastName();
        this.phoneNumber=teacher.getPhoneNumber();
        this.email=teacher.getEmail();
        this.registerDate=teacher.getRegisterDate();
    }
}
