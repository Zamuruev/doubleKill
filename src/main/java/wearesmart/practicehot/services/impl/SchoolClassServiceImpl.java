package wearesmart.practicehot.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wearesmart.practicehot.dtos.SchoolClassDTO;
import wearesmart.practicehot.models.SchoolClass;
import wearesmart.practicehot.models.Student;
import wearesmart.practicehot.models.Subject;
import wearesmart.practicehot.models.Teacher;
import wearesmart.practicehot.repositories.SchoolClassRepository;
import wearesmart.practicehot.services.SchoolClassService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {
    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SchoolClassDTO> getAllClasses() {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        return schoolClasses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SchoolClassDTO getClassById(Long id) {
        Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findById(id);
        return optionalSchoolClass.map(this::convertToDTO).orElse(null);
    }

    public SchoolClassDTO saveClass(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = convertToEntity(schoolClassDTO);
        schoolClass = schoolClassRepository.save(schoolClass);
        return convertToDTO(schoolClass);
    }

    public void deleteClassById(Long id) {
        schoolClassRepository.deleteById(id);
    }
    @Transactional
    public void transferStudentToClass(Long studentId, Long targetClassId) {
        Optional<SchoolClass> optionalTargetClass = schoolClassRepository.findById(targetClassId);
        if (optionalTargetClass.isPresent()) {
            SchoolClass targetClass = optionalTargetClass.get();
            Optional<Student> optionalStudent = targetClass.getStudents().stream()
                    .filter(student -> student.getId().equals(studentId))
                    .findFirst();
            if (optionalStudent.isPresent()) {
                // Найден студент в будущем классе - ничего не делаем
                return;
            }

            Optional<SchoolClass> optionalCurrentClass = schoolClassRepository.findByStudentId(studentId);
            if (optionalCurrentClass.isPresent()) {
                SchoolClass currentClass = optionalCurrentClass.get();
                Optional<Student> optionalStudentInCurrentClass = currentClass.getStudents().stream()
                        .filter(student -> student.getId().equals(studentId))
                        .findFirst();
                if (optionalStudentInCurrentClass.isPresent()) {
                    Student student = optionalStudentInCurrentClass.get();
                    currentClass.getStudents().remove(student);
                    targetClass.getStudents().add(student);
                    student.setSchoolClass(targetClass);
                    schoolClassRepository.save(currentClass);
                    schoolClassRepository.save(targetClass);
                }
            }
        }
    }

    // Метод для получения класса по ID студента
    public SchoolClassDTO getClassByStudentId(Long studentId) {
        Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findByStudentId(studentId);
        return optionalSchoolClass.map(this::convertToDTO).orElse(null);
    }

    // Методы маппинга
    private SchoolClassDTO convertToDTO(SchoolClass schoolClass) {
        SchoolClassDTO schoolClassDTO = modelMapper.map(schoolClass, SchoolClassDTO.class);
        schoolClassDTO.setStudentIds(
                schoolClass.getStudents().stream()
                        .map(Student::getId)
                        .collect(Collectors.toList())
        );
        schoolClassDTO.setTeacherIds(
                schoolClass.getTeachers().stream()
                        .map(Teacher::getId)
                        .collect(Collectors.toList())
        );
        schoolClassDTO.setSubjectIds(
                schoolClass.getSubjects().stream()
                        .map(Subject::getId)
                        .collect(Collectors.toList())
        );
        return schoolClassDTO;
    }

    private SchoolClass convertToEntity(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = modelMapper.map(schoolClassDTO, SchoolClass.class);
        List<Student> students = schoolClassDTO.getStudentIds().stream()
                .map(id -> { Student student = new Student(); student.setId(id);
                    return student; })
                .collect(Collectors.toList());
        schoolClass.setStudents(students);
        List<Teacher> teachers = schoolClassDTO.getTeacherIds().stream()
                .map(id -> {
                    Teacher teacher = new Teacher();
                    teacher.setId(id);
                    return teacher;
                })
                .collect(Collectors.toList());
        schoolClass.setTeachers(teachers);
        List<Subject> subjects = schoolClassDTO.getSubjectIds().stream()
                .map(id -> {
                    Subject subject = new Subject();
                    subject.setId(id);
                    return subject;
                })
                .collect(Collectors.toList());
        schoolClass.setSubjects(subjects);
        return schoolClass;
    }
}
