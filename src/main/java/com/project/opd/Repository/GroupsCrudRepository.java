package com.project.opd.Repository;

import com.project.opd.Database.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupsCrudRepository extends CrudRepository<Groups, Long> {
}
