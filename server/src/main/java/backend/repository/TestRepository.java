package backend.repository;

import backend.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TestRepository extends JpaRepository<TestModel, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM test_model WHERE username = ?1")
    List<TestModel> findByUsername(String username);
}
