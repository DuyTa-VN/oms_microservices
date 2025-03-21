package vn.duyta.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import vn.duyta.userservice.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKeycloakUserId(String keycloakUserId);
    Optional<User> findByUsername(String username);
    void deleteByKeycloakUserId(String keycloakUserId);
}
