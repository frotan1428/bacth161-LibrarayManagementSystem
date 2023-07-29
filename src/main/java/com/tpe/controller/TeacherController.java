package com.tpe.controller;

import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDto;
import com.tpe.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/query")////http://localhost:8080/teachers/query?id=1
    public ResponseEntity<Teacher> findTeacherByIdWIthParam(@RequestParam Long id){
      Teacher teacher =  teacherService.getTeacherById(id);
      return new ResponseEntity<>(teacher,HttpStatus.OK);

    }

    // find the teacher by lastName

    @GetMapping("/byLastName")//http://localhost:8080/teacher/byLastName?lastName="Ali"
    public ResponseEntity<List<Teacher>>  getTeacherByLastName(@RequestParam String lastName){

      List<Teacher> teachers=  teacherService.getTeacherByLastName(lastName);
      return new ResponseEntity<>(teachers,HttpStatus.OK);
    }

    /*
    @PutMapping("/{id}")//http://localhost:8080/teachers/1

    public ResponseEntity<Map<String,String>> updateTeacher(@Valid @PathVariable Long id ,@RequestBody Teacher teacher){

        teacherService.updateTeacherById(id,teacher);

        Map<String,String> map= new HashMap<>();
        map.put("message","Teacher with id" + id + " has been updated successfully ");
        map.put("status","True");
        return ResponseEntity.ok(map);
    }



     */


    @GetMapping("/page")//http://localhost:8080/teachers/page?page=1&size=3&sort=name&direction=ASC/DESC

    public ResponseEntity<Page<Teacher>> getTeacherByPage(@RequestParam("page") int page,
                                                          @RequestParam("size") int size,
                                                          @RequestParam("sort") String  prop,
                                                          @RequestParam("direction")Sort.Direction direction){

      Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

      Page<Teacher>  pageOfTeachers = teacherService.getTeacherWithPage(pageable);
      return ResponseEntity.ok(pageOfTeachers);

    }

    @PutMapping("/{teacherId}")//http://localhost:8080/teachers/1

    public ResponseEntity<Map<String,String>> updateTeacher( @PathVariable Long teacherId ,
                                                             @Valid @RequestBody TeacherDto teacherDto){

        teacherService.updateTeacherByDto(teacherId,teacherDto);
        Map<String,String> map= new HashMap<>();
        map.put("message","Teacher with id" + teacherId + " has been updated successfully ");
        map.put("status","True");
        return ResponseEntity.ok(map);
    }


    // get  teacher By Id over   Dto

    @GetMapping("query/dto")////http://localhost:8080/teachers/query/dto?id=1;

    public ResponseEntity<TeacherDto> getTeacherByDto(@RequestParam("id") Long id){
       TeacherDto teacherDto = teacherService.getTeacherByDto(id);
       return ResponseEntity.ok(teacherDto);
    }


    //get Teacher By name using JPQL

    @GetMapping("/byNameUsingJPQL")//http://localhost:8080/tachers/byNameUsingJPQL?name="Ali"
    public ResponseEntity<List<Teacher>> getTeacherByNameUsingJPQL(@RequestParam String name){

      List<Teacher> teachers =  teacherService.getTeacherByNameUsingJPQL(name);

      return new ResponseEntity<>(teachers,HttpStatus.OK);
    }











}
