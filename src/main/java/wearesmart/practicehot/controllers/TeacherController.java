package wearesmart.practicehot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearesmart.practicehot.dtos.TeacherDTO;
import wearesmart.practicehot.services.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        TeacherDTO teacherDTO = teacherService.getTeacherById(id);
        if (teacherDTO != null) {
            return ResponseEntity.ok(teacherDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public TeacherDTO saveTeacher(@RequestBody TeacherDTO teacherDTO) {
        return teacherService.saveTeacher(teacherDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }
}
