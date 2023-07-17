package wearesmart.practicehot.dtos;

import java.util.List;

public class SubjectDTO {
    private Long id;
    private String name;
    
    private List<Long> schoolClassIds;

    public SubjectDTO(Long id, String name, List<Long> schoolClassIds) {
        this.id = id;
        this.name = name;
        this.schoolClassIds = schoolClassIds;
    }

    public SubjectDTO() {}

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

    public List<Long> getSchoolClassIds() {
        return schoolClassIds;
    }

    public void setSchoolClassIds(List<Long> schoolClassIds) {
        this.schoolClassIds = schoolClassIds;
    }

}