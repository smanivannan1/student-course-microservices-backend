package com.sachin.user.repository;

import com.sachin.user.entity.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameIgnoreCaseOrNameIgnoreCaseOrEmailIgnoreCase(String username, String name, String email);


}
