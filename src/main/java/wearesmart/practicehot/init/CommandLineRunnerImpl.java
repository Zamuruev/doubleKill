package wearesmart.practicehot.init;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import wearesmart.practicehot.dtos.SchoolClassDTO;
import wearesmart.practicehot.dtos.StudentDTO;
import wearesmart.practicehot.dtos.SubjectDTO;
import wearesmart.practicehot.dtos.TeacherDTO;
import wearesmart.practicehot.services.SchoolClassService;
import wearesmart.practicehot.services.StudentService;
import wearesmart.practicehot.services.SubjectService;
import wearesmart.practicehot.services.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void run(String... args) throws Exception {
        seedData();
    }

    private void seedData() {
        // Добавление в БД записей
        SchoolClassDTO schoolClass1 = new SchoolClassDTO();
        schoolClass1.setName("УВП-211");
        schoolClass1.setStudentIds(new ArrayList<>());
        schoolClass1.setTeacherIds(new ArrayList<>());
        schoolClass1.setSubjectIds(new ArrayList<>());

        SchoolClassDTO schoolClass2 = new SchoolClassDTO();
        schoolClass2.setName("УВП-212");
        schoolClass2.setStudentIds(new ArrayList<>());
        schoolClass2.setTeacherIds(new ArrayList<>());
        schoolClass2.setSubjectIds(new ArrayList<>());

        TeacherDTO teacher1 = new TeacherDTO();
        teacher1.setName("Заманов Евгений Альбертович");
        teacher1.setSubjectIds(new ArrayList<>());

        TeacherDTO teacher2 = new TeacherDTO();
        teacher2.setName("Разживайкин Игорь Станиславович");
        teacher2.setSubjectIds(new ArrayList<>());

        TeacherDTO teacher3 = new TeacherDTO();
        teacher3.setName("Кокин Игорь Станиславович");
        teacher3.setSubjectIds(new ArrayList<>());

        TeacherDTO teacher4 = new TeacherDTO();
        teacher4.setName("Антонова Елена Вячеславовна");
        teacher4.setSubjectIds(new ArrayList<>());

        SubjectDTO subject1 = new SubjectDTO();
        subject1.setName("Программирование");
        subject1.setSchoolClassIds(new ArrayList<>());

        SubjectDTO subject2 = new SubjectDTO();
        subject2.setName("Информационные технологии");
        subject2.setSchoolClassIds(new ArrayList<>());

        SubjectDTO subject3 = new SubjectDTO();
        subject3.setName("Физика");
        subject3.setSchoolClassIds(new ArrayList<>());

        SubjectDTO subject4 = new SubjectDTO();
        subject4.setName("Математика");
        subject4.setSchoolClassIds(new ArrayList<>());

        subject1 = subjectService.saveSubject(subject1);
        subject2 = subjectService.saveSubject(subject2);
        subject3 = subjectService.saveSubject(subject3);
        subject4 = subjectService.saveSubject(subject4);

        List<Long> id_subjects1 = new ArrayList<>();
        id_subjects1.add(subject1.getId());
        id_subjects1.add(subject2.getId());
        schoolClass1.setSubjectIds(id_subjects1);

        // Создание связей между предметами и преподавателями
        teacher1.getSubjectIds().add(subject1.getId());
        teacher2.getSubjectIds().add(subject2.getId());
        teacher3.getSubjectIds().add(subject3.getId());
        teacher4.getSubjectIds().add(subject4.getId());

        teacher1 = teacherService.saveTeacher(teacher1);
        teacher2 = teacherService.saveTeacher(teacher2);

        List<Long> id_teachers1 = new ArrayList<>();
        id_teachers1.add(teacher1.getId());
        id_teachers1.add(teacher2.getId());
        schoolClass1.setTeacherIds(id_teachers1);

        schoolClass1 = schoolClassService.saveClass(schoolClass1);

        List<Long> id_subjects2 = new ArrayList<>();
        id_subjects2.add(subject1.getId());
        id_subjects2.add(subject3.getId());
        id_subjects2.add(subject4.getId());
        schoolClass2.setSubjectIds(id_subjects2);


        teacher3 = teacherService.saveTeacher(teacher3);
        teacher4 = teacherService.saveTeacher(teacher4);

        List<Long> id_teachers2 = new ArrayList<>();
        id_teachers2.add(teacher1.getId());
        id_teachers2.add(teacher3.getId());
        id_teachers2.add(teacher4.getId());
        schoolClass2.setTeacherIds(id_teachers2);

        schoolClass2 = schoolClassService.saveClass(schoolClass2);

        StudentDTO student1 = new StudentDTO();
        student1.setName("Замуруев Роман Романович");
        student1.setSchoolClassId(schoolClass1.getId());

        StudentDTO student2 = new StudentDTO();
        student2.setName("Брежнева Алена Владимировна");
        student2.setSchoolClassId(schoolClass1.getId());

        StudentDTO student3 = new StudentDTO();
        student3.setName("Лягашкина Ксения Алексеевна");
        student3.setSchoolClassId(schoolClass2.getId());

        StudentDTO student4 = new StudentDTO();
        student4.setName("Карпушин Андрей Олегович");
        student4.setSchoolClassId(schoolClass2.getId());

        student1 = studentService.saveStudent(student1);
        student2 = studentService.saveStudent(student2);
        student3 = studentService.saveStudent(student3);
        student4 = studentService.saveStudent(student4);


        // Выполнение перевода студента из одного класса в другой
        schoolClassService.transferStudentToClass(student1.getId(), schoolClass2.getId());
    }
}
