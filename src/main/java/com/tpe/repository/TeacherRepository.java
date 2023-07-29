package com.tpe.repository;

import com.tpe.domain.Teacher;
import com.tpe.dto.TeacherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository  extends JpaRepository<Teacher,Long> {
    Teacher findByEmail(String email);

    List<Teacher> findByLastName(String lastName);


    @Query("SELECT new com.tpe.dto.TeacherDto (t) FROM Teacher t WHERE t.id=:id")
    Optional<TeacherDto> findTeacherByDto(@Param("id") Long id);


    @Query("SELECT t FROM Teacher t WHERE t.name=:name")
    List<Teacher> findByNameUsingJPQL(@Param("name") String name);

    @Query(value = "SELECT * FROM tbl_teacher t WHERE s.name =:tName", nativeQuery = true)
    List<Teacher> findStudentsByGradeWithSQL(@Param("tName") String name);


}
