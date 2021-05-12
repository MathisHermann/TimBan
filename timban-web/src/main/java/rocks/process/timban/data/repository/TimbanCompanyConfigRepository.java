package rocks.process.timban.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rocks.process.timban.data.domain.TimbanCompanyConfig;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 12.05.2021
 * Edit:
 */

public interface TimbanCompanyConfigRepository extends JpaRepository<TimbanCompanyConfig, Long> {
}
