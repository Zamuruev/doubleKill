package wearesmart.practicehot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wearesmart.practicehot.models.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAll();

    Optional<Subject> findById(Long id);

    Subject save(Subject subject);

    void deleteById(Long id);

    @Query("SELECT s FROM Subject s JOIN s.schoolClasses sc JOIN sc.students st WHERE st.id = :studentId")
    List<Subject> findSubjectsByStudentId(@Param("studentId") Long studentId);

}
