package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers")//http://localhost:8080/teachers
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping//http://localhost:8080/teachers
    public ResponseEntity<Map<String,String>> saveTeacher(@Valid @RequestBody Teacher teacher){

       Teacher saveTeacher = teacherService.saveTeacher(teacher);
       Map<String,String> map= new HashMap<>();
       map.put("message","Teacher has been saved successfully ... ");
       map.put("status","True");
       return new ResponseEntity<>(map, HttpStatus.CREATED);//201
    }



    /*
    @PostMapping//http://localhost:8080/teachers
    public ResponseEntity<Teacher> saveTeacher(@Valid @RequestBody Teacher teacher){

        Teacher saveTeacher = teacherService.saveTeacher(teacher);
        Map<String,String> map= new HashMap<>();
        map.put("message","Teacher has been saved successfully ... ");
        map.put("status","True");
        return new ResponseEntity<>(saveTeacher, HttpStatus.CREATED);//201
    }

     */

    @GetMapping//http://localhost:8080/teachers

    public ResponseEntity<List<Teacher>> getALLTeacher(){
       List<Teacher> teachers = teacherService.findAllTeacher();
       return new ResponseEntity<>(teachers,HttpStatus.OK);
    }

    @GetMapping("/{id}")//http://localhost:8080/teachers/1

    public ResponseEntity<Teacher> findTeacherByIdWIthPathVariable(@PathVariable  Long id){
       Teacher teacher = teacherService.getTeacherById(id);
       return  new ResponseEntity<>(teacher,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("id") Long id){
        teacherService.deleteTeacherById(id);
        String message="Teacher with id "+ id + " has been deleted ";
        return new ResponseEntity<>(message ,HttpStatus.OK);

    }



}
