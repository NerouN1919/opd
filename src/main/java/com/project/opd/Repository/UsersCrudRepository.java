package com.project.opd.Repository;

import com.project.opd.Database.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersCrudRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByLogin(String login);
}
