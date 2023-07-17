package wearesmart.practicehot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearesmart.practicehot.models.Teacher;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findById(Long id);

    List<Teacher> findAll();

    Teacher save(Teacher teacher);

    void deleteById(Long id);
}
