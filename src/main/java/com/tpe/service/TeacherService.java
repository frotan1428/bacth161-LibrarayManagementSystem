package com.tpe.service;


import com.tpe.domain.Teacher;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
