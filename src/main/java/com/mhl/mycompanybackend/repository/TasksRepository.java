package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findAllByExecutorEquals(Users executor);
    List<Tasks> findAllByCreatorEquals(Users creator);
    List<Tasks> findAllByMembersContains(Users member);
}
