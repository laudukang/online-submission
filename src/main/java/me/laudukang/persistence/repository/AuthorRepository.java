package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/23
 * <p>Time: 16:26
 * <p>Version: 1.0
 */
@Repository
public interface AuthorRepository extends JpaRepository<OsAuthor, Integer> {
}
