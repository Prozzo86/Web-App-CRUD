package com.bogdanovic.student;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("/studenti")
    public String showStudentList(Model model) {
        List<Student> listStudenti = service.listAll();
        model.addAttribute("listStudenti", listStudenti);

        return "studenti";
    }

    @GetMapping("/studenti/novi")
    public String showNewForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Dodaj studenta!");
        return "student_form";
    }

    @PostMapping("/studenti/spremi")
    public String saveStudent(Student student, RedirectAttributes ra) {
        service.save(student);
        ra.addFlashAttribute("poruka", "Student uspješno dodan!");
        return "redirect:/studenti";
    }
    @GetMapping("/studenti/uredi/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Student student = service.get(id);
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Uredi student (ID: " + id + ")");
            return "student_form";
        } catch (StudentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/studenti";
        }
    }

    @GetMapping("/studenti/delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Student ID: "+id+" je obrisan.");
        } catch (StudentNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/studenti";

    }


}


