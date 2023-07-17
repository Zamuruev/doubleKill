package wearesmart.practicehot.dtos;

import java.util.List;

public class TeacherDTO {
    private Long id;
    private String name;
    private List<Long> subjectIds;

    public TeacherDTO(Long id, String name, List<Long> subjectIds) {
        this.id = id;
        this.name = name;
        this.subjectIds = subjectIds;
    }

    public TeacherDTO() {}

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

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}