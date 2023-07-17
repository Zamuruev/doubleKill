package wearesmart.practicehot.services;

import wearesmart.practicehot.dtos.SubjectDTO;

import java.util.List;

public interface SubjectService {
    SubjectDTO getSubjectById(Long id);

    SubjectDTO saveSubject(SubjectDTO subjectDTO);

    void deleteSubjectById(Long id);

    List<SubjectDTO> getAllSubjects();

    void removeSubjectFromClass(Long id);

    void removeSubjectFromTeacher(Long id);
}
