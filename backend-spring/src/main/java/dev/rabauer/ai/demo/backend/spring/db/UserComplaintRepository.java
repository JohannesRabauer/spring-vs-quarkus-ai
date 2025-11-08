package dev.rabauer.ai.demo.backend.spring.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserComplaintRepository extends JpaRepository<UserComplaint, Long> {
}
