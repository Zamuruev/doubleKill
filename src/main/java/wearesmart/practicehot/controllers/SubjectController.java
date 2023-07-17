package wearesmart.practicehot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearesmart.practicehot.dtos.SubjectDTO;
import wearesmart.practicehot.services.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subjectDTO = subjectService.getSubjectById(id);
        if (subjectDTO != null) {
            return ResponseEntity.ok(subjectDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public SubjectDTO saveSubject(@RequestBody SubjectDTO subjectDTO) {
        return subjectService.saveSubject(subjectDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        // Удаление связи между предметом и классом
        subjectService.removeSubjectFromClass(id);

        // Удаление связи между предметом и учителем
        subjectService.removeSubjectFromTeacher(id);

        // Удаление предмета по идентификатору
        subjectService.deleteSubjectById(id);

        return ResponseEntity.noContent().build();
    }
}
