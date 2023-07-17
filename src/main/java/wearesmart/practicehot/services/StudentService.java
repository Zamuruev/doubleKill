package wearesmart.practicehot.services;

import wearesmart.practicehot.dtos.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(Long id);

    StudentDTO saveStudent(StudentDTO studentDTO);

    void deleteStudentById(Long id);
    void transfer(Long studentId, Long targetClassId);
}
