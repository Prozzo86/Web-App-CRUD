package com.bogdanovic;


import com.bogdanovic.student.Student;
import com.bogdanovic.student.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)

public class StudentRepositoryTests  {

    @Autowired private StudentRepository repo;

    @Test
    public void testAddNew()
    {
        Student student = new Student();
        student.setEmail("ivoperic@gmail.com");
        student.setLozinka("peric1234");
        student.setIme("peric_ivo");
        student.setPrezime("ivovivoviovov");
        student.setDatum("10.11.1986");

        Student savedStudent = repo.save(student);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll() {
        Iterable<Student> studenti = repo.findAll();
        Assertions.assertThat(studenti).hasSizeGreaterThan(0);

        for (Student student : studenti) {
            System.out.println(student);
        }
    }

    @Test
    public void testUpdate()
    {
        Integer studentId = 1;
        Optional<Student> optionalStudent = repo.findById(studentId);
        Student student = optionalStudent.get();
        student.setLozinka("12345678");
        repo.save(student);

        Student updateStudent = repo.findById(studentId).get();
        Assertions.assertThat(updateStudent.getLozinka()).isEqualTo("12345678");
    }
    @Test
    public void testGet()
    {
        Integer studentId = 2;
        Optional<Student> optionalStudent = repo.findById(studentId);

        Assertions.assertThat(optionalStudent).isPresent();
        System.out.println(optionalStudent.get());
    }
    @Test
    public void testDelete()
    {
        Integer studentId = 2;
        repo.deleteById(studentId);

        Optional<Student> optionalStudent = repo.findById(studentId);
        Assertions.assertThat(optionalStudent).isNotPresent();
    }
}
