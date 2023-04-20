package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

}