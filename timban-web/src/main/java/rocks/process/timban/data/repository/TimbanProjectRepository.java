package rocks.process.timban.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.timban.data.domain.TimbanProject;

@Repository
public interface TimbanProjectRepository extends JpaRepository<TimbanProject, Long> {
}
