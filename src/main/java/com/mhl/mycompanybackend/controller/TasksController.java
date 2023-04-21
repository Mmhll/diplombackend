package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.pojo.*;
import com.mhl.mycompanybackend.service.TasksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TasksController {

    final TasksService service;

    public TasksController(TasksService service) {
        this.service = service;
    }


    @GetMapping("/get_all_tasks")
    public ResponseEntity<?> getAllTasks(@RequestBody IdRequest request) {
        return service.getAllTasks(request.getId());
    }

    @GetMapping("/get_task")
    public ResponseEntity<?> getTask(@RequestBody Long id) {
        return service.getTask(id);
    }

    @PostMapping("/save_task")
    public ResponseEntity<?> saveTask(@RequestBody Tasks task) {
        return service.saveTask(task);
    }

    @DeleteMapping("/delete_task")
    public ResponseEntity<MessageResponse> deleteTask(@RequestBody Long id) {
        return service.deleteTaskById(id);
    }

/*    @PostMapping("/update_status")
    public ResponseEntity<MessageResponse> updateStatus(@RequestBody StatusRequest request) {
        return service.updateStatus(request);
    }

    @PostMapping("/update_task")
    public ResponseEntity<MessageResponse> updateTask(@RequestBody UpdateTaskRequest request) {
        return service.updateTask(request);
    }*/
}
