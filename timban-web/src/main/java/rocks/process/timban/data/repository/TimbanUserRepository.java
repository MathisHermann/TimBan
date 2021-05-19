/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rocks.process.timban.data.domain.TimbanUser;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 19.05.2021
 */

@Repository
public interface TimbanUserRepository extends JpaRepository<TimbanUser, Long> {
	TimbanUser findByEmail(String email);
	TimbanUser findByEmailAndIdNot(String email, Long agentId);
}
