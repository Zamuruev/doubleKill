package wearesmart.practicehot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wearesmart.practicehot.models.SchoolClass;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findAll();
    Optional<SchoolClass> findById(Long id);

    SchoolClass save(SchoolClass schoolClass);

    void deleteById(Long id);

    @Query("SELECT sc FROM SchoolClass sc JOIN sc.students s WHERE s.id = :studentId")
    Optional<SchoolClass> findByStudentId(@Param("studentId") Long studentId);
}
