/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.timban.data.domain.TimBanUser;

@Repository
public interface TimBanUserRepository extends JpaRepository<TimBanUser, Long> {
	TimBanUser findByEmail(String email);
	TimBanUser findByEmailAndIdNot(String email, Long agentId);
}
