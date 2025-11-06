package dev.rabauer.ai.demo.frontend.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserComplaintRepository extends JpaRepository<UserComplaint, Long> {
}
