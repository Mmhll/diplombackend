package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.pojo.MessageResponse;
import com.mhl.mycompanybackend.pojo.StatusRequest;
import com.mhl.mycompanybackend.pojo.UpdateTaskRequest;
import com.mhl.mycompanybackend.repository.TasksRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TasksService {
    final TasksRepository tasksRepository;
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }
    public ResponseEntity<?> getAllTasks(Long id) {
        return ResponseEntity.ok().body(""); /*.body(tasksRepository.(id));*/
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
