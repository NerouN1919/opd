package com.project.opd.Repository;

import com.project.opd.Database.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupsCrudRepository extends CrudRepository<Groups, Long> {
}
