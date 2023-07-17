package wearesmart.practicehot.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wearesmart.practicehot.dtos.SubjectDTO;
import wearesmart.practicehot.models.SchoolClass;
import wearesmart.practicehot.models.Subject;
import wearesmart.practicehot.models.Teacher;
import wearesmart.practicehot.repositories.SchoolClassRepository;
import wearesmart.practicehot.repositories.SubjectRepository;
import wearesmart.practicehot.repositories.TeacherRepository;
import wearesmart.practicehot.services.SubjectService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SubjectDTO getSubjectById(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.map(this::convertToDTO).orElse(null);
    }

    public SubjectDTO saveSubject(SubjectDTO subjectDTO) {
        Subject subject = convertToEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        return convertToDTO(subject);
    }

    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

    public void removeSubjectFromClass(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject != null) {
            // Получите список классов, в которых есть этот предмет
            List<SchoolClass> classesWithSubject = subject.getSchoolClasses();

            // Удалите предмет из списка предметов у каждого класса
            for (SchoolClass schoolClass : classesWithSubject) {
                schoolClass.getSubjects().remove(subject);
            }

            // Сохраните изменения, чтобы обновить связи в базе данных
            schoolClassRepository.saveAll(classesWithSubject);
        }
    }

    public void removeSubjectFromTeacher(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject != null) {
            // Получите список учителей, которые ведут этот предмет
            List<Teacher> teachersWithSubject = subject.getTeachers();

            // Удалите предмет из списка предметов у каждого учителя
            for (Teacher teacher : teachersWithSubject) {
                teacher.getSubjects().remove(subject);
            }

            // Сохраните изменения, чтобы обновить связи в базе данных
            teacherRepository.saveAll(teachersWithSubject);
        }
    }

    // Методы маппинга
    private SubjectDTO convertToDTO(Subject subject) {
        SubjectDTO subjectDTO = modelMapper.map(subject, SubjectDTO.class);
        subjectDTO.setSchoolClassIds(
                subject.getSchoolClasses().stream()
                        .map(SchoolClass::getId)
                        .collect(Collectors.toList())
        );
        return subjectDTO;
    }

    private Subject convertToEntity(SubjectDTO subjectDTO) {
        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        List<SchoolClass> schoolClasses = subjectDTO.getSchoolClassIds().stream()
                .map(id -> {
                    SchoolClass schoolClass = new SchoolClass();
                    schoolClass.setId(id);
                    return schoolClass;
                })
                .collect(Collectors.toList());
        subject.setSchoolClasses(schoolClasses);
        return subject;
    }
}
