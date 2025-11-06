package dev.rabauer.ai.demo.backend.quarkus.db;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserComplaintRepository implements PanacheRepository<UserComplaint> {
}
