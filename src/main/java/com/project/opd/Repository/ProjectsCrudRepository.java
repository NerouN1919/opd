package com.project.opd.Repository;

import com.project.opd.Database.Projects;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsCrudRepository extends CrudRepository<Projects, Long> {
}
