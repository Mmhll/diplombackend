package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
   /* @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM tasks t, users u LEFT JOIN tasks_user tu on t.executor_id = tu.user_id LEFT JOIN ")
    List<Tasks> findAllTasksOfUserExecutor();
*/

}
