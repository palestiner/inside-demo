package com.palestiner.insidedemo.repo;

import com.palestiner.insidedemo.model.Message;
import com.palestiner.insidedemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(nativeQuery = true, value =
    "select * from message m join app_user u on m.user_id = u.id where u.username = :username order by m.id desc limit :limit")
    List<Message> getLastMessages(@Param("username") String username, @Param("limit") int limit);
}
