package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.model.Users;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.repository.TasksRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TasksService {
    final TasksRepository tasksRepository;
    final
    UserRepository userRepository;
    public TasksService(TasksRepository tasksRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity<?> getAllTasksWhereExecutor(Long id) {
        Users executor = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(tasksRepository.findAllByExecutorEquals(executor));
    }

    public ResponseEntity<?> getAllTasksWhereCreator(Long id) {
        Users creator = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(tasksRepository.findAllByCreatorEquals(creator));
    }

    public ResponseEntity<?> getAllTasksWhereMember(Long id) {
        Users member = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok().body(tasksRepository.findAllByMembersContains(member));
    }

    public ResponseEntity<?> getTask(Long id) {
        try {
            return ResponseEntity.ok().body(tasksRepository.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> saveTask(Tasks task) {
        tasksRepository.save(task);
        return ResponseEntity.ok().body(new MessageResponse("Task was saved"));
    }

    public ResponseEntity<MessageResponse> deleteTaskById(Long id){
        try {
            tasksRepository.deleteById(id);
            return ResponseEntity.ok().body(new MessageResponse("Task was deleted"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }
/*

    public ResponseEntity<MessageResponse> updateStatus(StatusRequest request){
        try {
            tasksRepository.updateStatus(request.getId(), request.getStatus_id());
            return ResponseEntity.ok().body(new MessageResponse("Task was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> updateTask(UpdateTaskRequest request){
        try{
            tasksRepository.updateTask(request.getExecutor_id(), request.getName(), request.getDescription(), request.getDeadline());
            return ResponseEntity.ok().body(new MessageResponse("Task was updated"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

*/

}
