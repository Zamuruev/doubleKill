package wearesmart.practicehot.services.impl;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wearesmart.practicehot.dtos.StudentDTO;
import wearesmart.practicehot.models.SchoolClass;
import wearesmart.practicehot.models.Student;
import wearesmart.practicehot.repositories.SchoolClassRepository;
import wearesmart.practicehot.repositories.StudentRepository;
import wearesmart.practicehot.services.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.map(this::convertToDTO).orElse(null);
    }

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        student = studentRepository.save(student);
        return convertToDTO(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }


    // Методы маппинга
    private StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        if (student.getSchoolClass() != null) {
            studentDTO.setSchoolClassId(student.getSchoolClass().getId());
        }
        return studentDTO;
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        if (studentDTO.getSchoolClassId() != null) {
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId(studentDTO.getSchoolClassId());
            student.setSchoolClass(schoolClass);
        }
        return student;
    }
}
