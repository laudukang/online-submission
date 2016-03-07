package me.laudukang.persistence.repository;

import me.laudukang.persistence.model.OsMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/1/30
 * <p>Time: 23:37
 * <p>Version: 1.0
 */
@Repository
public interface MessageRepository extends JpaRepository<OsMessage, Integer>, MessageRepositoryCustom {
    //@Query("select message.id,message.title,message.content,message.postTime,message.osAdmin.account,message.osUser.account from OsMessage message where message.osUser.id=:id")
    //List<Object[]> findAllByUser(@Param("id") int id);
}
