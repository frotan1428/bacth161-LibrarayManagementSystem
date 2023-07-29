package com.tpe.service;


import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDto;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher) {

     Teacher existTeacher = teacherRepository.findByEmail(teacher.getEmail());
     if (existTeacher!=null){
         throw new ConflictException("Teacher with the same +" + teacher.getEmail() + "  already exist ..");
     }
     return teacherRepository.save(teacher);

    }

    public List<Teacher> findAllTeacher() {
       List<Teacher> teacherList = teacherRepository.findAll(); //[T1-T2]  []
        if (teacherList.isEmpty()){
            throw  new ResourceNotFoundException("Teacher List is Empty ..");//[]
        }
        return teacherList;
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Teacher is not found with id : "+id));

    }

    public void deleteTeacherById(Long id) {

        Teacher teacher=  getTeacherById(id);
        teacherRepository.delete(teacher);

    }

    public List<Teacher> getTeacherByLastName(String lastName) {

        return teacherRepository.findByLastName(lastName);

    }

    //1- we need check the teacher is exist
    //2- check email

    public void updateTeacherById(Long id, Teacher teacher) {

        Teacher  existTeacher = getTeacherById(id);

        //frotan@1234.com           //frotan@1234.com
        if (!existTeacher.getEmail().equals(teacher.getEmail())){
            Teacher teacherWithUpdateEmail = teacherRepository.findByEmail(teacher.getEmail());
            if (teacherWithUpdateEmail!=null){
                throw  new ConflictException("email already is exist for another teacher .");
            }

        }

        existTeacher.setName(teacher.getName());
        existTeacher.setLastName(teacher.getLastName());
        existTeacher.setEmail(teacher.getEmail());
        existTeacher.setPhoneNumber(teacher.getPhoneNumber());
        existTeacher.setBooks(teacher.getBooks());

        teacherRepository.save(existTeacher);
    }


    public Page<Teacher> getTeacherWithPage(Pageable pageable) {
       return teacherRepository.findAll(pageable);
    }

    public void updateTeacherByDto(Long teacherId, TeacherDto teacherDto) {

        Teacher existTeacher = getTeacherById(teacherId);

        if (!existTeacher.getEmail().equals(teacherDto.getEmail())){
            Teacher teacherWithUpdateEmail = teacherRepository.findByEmail(teacherDto.getEmail());
            if (teacherWithUpdateEmail!=null){
                throw  new ConflictException("email already is exist for another teacher .");
            }

        }

        //update the filed of the existing teacher with new values

        existTeacher.setName(teacherDto.getName());
        existTeacher.setLastName(teacherDto.getLastName());
        existTeacher.setEmail(teacherDto.getEmail());
        existTeacher.setPhoneNumber(teacherDto.getPhoneNumber());

        teacherRepository.save(existTeacher);


    }

    public TeacherDto getTeacherByDto(Long id) {

        return teacherRepository.findTeacherByDto(id).orElseThrow(()->
                new ResourceNotFoundException("Teacher whose id " +id + " not found "));

    }

    public List<Teacher> getTeacherByNameUsingJPQL(String name) {
       //  return teacherRepository.findByNameUsingJPQL(name);
     List<Teacher> teacher= teacherRepository.findByNameUsingJPQL(name);
     if (teacher.isEmpty()){
         throw  new ResourceNotFoundException("No Teacher found with :" +name);
     }
     return  teacher;

    }
}
