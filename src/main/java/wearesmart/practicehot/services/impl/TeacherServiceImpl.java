package wearesmart.practicehot.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wearesmart.practicehot.dtos.TeacherDTO;
import wearesmart.practicehot.models.SchoolClass;
import wearesmart.practicehot.models.Subject;
import wearesmart.practicehot.models.Teacher;
import wearesmart.practicehot.repositories.SchoolClassRepository;
import wearesmart.practicehot.repositories.TeacherRepository;
import wearesmart.practicehot.services.TeacherService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TeacherDTO getTeacherById(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        return optionalTeacher.map(this::convertToDTO).orElse(null);
    }

    public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = convertToEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return convertToDTO(teacher);
    }

    public void deleteTeacherById(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher != null) {
            // Получите список классов, в которых преподаватель ведет уроки
            List<SchoolClass> classesWithTeacher = teacher.getSchoolClasses();

            // Удалите преподавателя из списка преподавателей у каждого класса
            for (SchoolClass schoolClass : classesWithTeacher) {
                schoolClass.getTeachers().remove(teacher);
            }

            // Сохраните изменения в таблице связи, чтобы удалить записи преподавателя из классов
            schoolClassRepository.saveAll(classesWithTeacher);

            // Теперь можно удалить самого преподавателя
            teacherRepository.deleteById(teacherId);
        }
    }

    // Методы маппинга
    private TeacherDTO convertToDTO(Teacher teacher) {
        TeacherDTO teacherDTO = modelMapper.map(teacher, TeacherDTO.class);
        teacherDTO.setSubjectIds(
                teacher.getSubjects().stream()
                        .map(Subject::getId)
                        .collect(Collectors.toList())
        );
        return teacherDTO;
    }

    private Teacher convertToEntity(TeacherDTO teacherDTO) {
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        List<Subject> subjects = teacherDTO.getSubjectIds().stream()
                .map(id -> {
                    Subject subject = new Subject();
                    subject.setId(id);
                    return subject;
                })
                .collect(Collectors.toList());
        teacher.setSubjects(subjects);
        return teacher;
    }
}
