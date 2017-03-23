package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    List<User> findByUsername(String username);
}
