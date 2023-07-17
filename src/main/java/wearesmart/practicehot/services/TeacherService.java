package wearesmart.practicehot.services;

import wearesmart.practicehot.dtos.TeacherDTO;

import java.util.List;

public interface TeacherService {
    TeacherDTO getTeacherById(Long id);

    TeacherDTO saveTeacher(TeacherDTO teacherDTO);

    void deleteTeacherById(Long id);

    List<TeacherDTO> getAllTeachers();
}
