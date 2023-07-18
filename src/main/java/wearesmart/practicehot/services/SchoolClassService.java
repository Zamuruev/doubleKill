package wearesmart.practicehot.services;

import wearesmart.practicehot.dtos.SchoolClassDTO;

import java.util.List;

public interface SchoolClassService {
    SchoolClassDTO getClassById(Long id);

    List<SchoolClassDTO> getAllClasses();

    SchoolClassDTO saveClass(SchoolClassDTO schoolClassDTO);

    void deleteClassById(Long id);

    void transferStudentToClass(Long studentId, Long classId);

}
