package wearesmart.practicehot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearesmart.practicehot.dtos.SchoolClassDTO;
import wearesmart.practicehot.services.SchoolClassService;

import java.util.List;

@RestController
@RequestMapping("/schoolclasses")
public class SchoolClassController {
    @Autowired
    private SchoolClassService schoolClassService;

    @GetMapping
    public List<SchoolClassDTO> getAllClasses() {
        return schoolClassService.getAllClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> getClassById(@PathVariable Long id) {
        SchoolClassDTO schoolClassDTO = schoolClassService.getClassById(id);
        if (schoolClassDTO != null) {
            return ResponseEntity.ok(schoolClassDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public SchoolClassDTO saveClass(@RequestBody SchoolClassDTO schoolClassDTO) {
        return schoolClassService.saveClass(schoolClassDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClassById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{classId}/transfer-student/{studentId}")
    public ResponseEntity<Void> transferStudentToClass(
            @PathVariable Long classId,
            @PathVariable Long studentId
    ) {
        schoolClassService.transferStudentToClass(studentId, classId);
        return ResponseEntity.ok().build();
    }

}
