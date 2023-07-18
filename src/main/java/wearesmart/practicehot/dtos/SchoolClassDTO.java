package wearesmart.practicehot.dtos;

import java.util.List;

public class SchoolClassDTO {
    private Long id;
    private String name;
    private List<Long> studentIds;
    private List<Long> teacherIds;
    private List<Long> subjectIds;

    public SchoolClassDTO(Long id, String name, List<Long> studentIds, List<Long> teacherIds, List<Long> subjectIds) {
        this.id = id;
        this.name = name;
        this.studentIds = studentIds;
        this.teacherIds = teacherIds;
        this.subjectIds = subjectIds;
    }

    public SchoolClassDTO() {}

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Long> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(List<Long> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}