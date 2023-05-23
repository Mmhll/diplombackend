package com.mhl.mycompanybackend.repository

import com.mhl.mycompanybackend.model.Tasks
import com.mhl.mycompanybackend.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface TasksRepository : JpaRepository<Tasks?, Long?> {
    fun findAllByExecutorEquals(executor: Users?): List<Tasks?>?
    fun findAllByCreatorEquals(creator: Users?): List<Tasks?>?
    fun findAllByMembersContains(member: Users?): List<Tasks?>?

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tasks " +
            "SET status = ?2 " +
            "WHERE id = ?1")
    fun updateStatus(id: Long?, status: String?)

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tasks " +
            "SET name = ?2, description = ?3, deadline = ?4 " +
            "WHERE id = ?1")
    fun updateTask(task_id: Long?, name: String?, description: String?, deadline: Date?)

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM tasks_user WHERE user_id = ?1 AND task_id = ?2")
    fun deleteMemberFromTask(user_id: Long?, task_id: Long?)

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO tasks_user(task_id, user_id) VALUES (?1, ?2)")
    fun addMemberToTask(task_id: Long?, user_id: Long?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE tasks SET executor_id = ?1 WHERE tasks.id = ?2")
    fun updateExecutor(executor_id: Long?, task_id: Long?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tasks_user WHERE tasks_user.task_id = ?1")
    fun deleteTasksUsersById(task_id: Long?)

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tasks WHERE id = ?1")
    fun deleteTasksById(task_id: Long?)
}
