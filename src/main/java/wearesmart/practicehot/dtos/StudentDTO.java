package wearesmart.practicehot.dtos;

public class StudentDTO {
    private Long id;
    private String name;
    private Long schoolClassId;

    public StudentDTO(Long id, String name, Long schoolClassId) {
        this.id = id;
        this.name = name;
        this.schoolClassId = schoolClassId;
    }

    public StudentDTO() {}

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

    public Long getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(Long schoolClassId) {
        this.schoolClassId = schoolClassId;
    }
}