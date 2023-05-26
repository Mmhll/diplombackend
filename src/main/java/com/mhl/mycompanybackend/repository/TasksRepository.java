package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findAllByExecutorEquals(Users executor);

    List<Tasks> findAllByCreatorEquals(Users creator);

    List<Tasks> findAllByMembersContains(Users member);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tasks " +
            "SET status = ?2, date_of_update =?3 " +
            "WHERE id = ?1")
    void updateStatus(Long id, String status, Timestamp datetime);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tasks " +
            "SET name = ?2, description = ?3, deadline = ?4 " +
            "WHERE id = ?1")
    void updateTask(Long task_id, String name, String description, Date deadline);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM tasks_user WHERE user_id = ?1 AND task_id = ?2")
    void deleteMemberFromTask(Long user_id, Long task_id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO tasks_user(task_id, user_id) VALUES (?1, ?2)")
    void addMemberToTask(Long task_id, Long user_id);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tasks SET executor_id = ?1 WHERE tasks.id = ?2")
    void updateExecutor(Long executor_id, Long task_id);


    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tasks_user WHERE tasks_user.task_id = ?1")
    void deleteTasksUsersById(Long task_id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tasks WHERE id = ?1")
    void deleteTasksById(Long task_id);
}
