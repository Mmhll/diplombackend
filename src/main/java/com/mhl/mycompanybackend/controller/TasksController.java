package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.pojo.*;
import com.mhl.mycompanybackend.service.TasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api( tags = "Tasks")
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class TasksController {

    final TasksService service;

    public TasksController(TasksService service) {
        this.service = service;
    }


    
    @GetMapping("/get_all_tasks_executor")
    public ResponseEntity<?> getAllTasksExecutor(@RequestParam String id) {
        return service.getAllTasksWhereExecutor(Long.parseLong(id));
    }

    
    @GetMapping("/get_all_tasks_creator")
    public ResponseEntity<?> getAllTasksCreator(@RequestParam String id) {
        return service.getAllTasksWhereCreator(Long.parseLong(id));
    }

    
    @GetMapping("/get_all_tasks_member")
    public ResponseEntity<?> getAllTasksMember(@RequestParam String id) {
        return service.getAllTasksWhereMember(Long.parseLong(id));
    }

    
    @GetMapping("/get_task")
    public ResponseEntity<?> getTask(@RequestParam String id) {
        return service.getTask(Long.parseLong(id));
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PostMapping("/save_task")
    public ResponseEntity<?> saveTask(@RequestBody TaskRequest task) {
        return service.saveTask(task);
    }


    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_task")
    public ResponseEntity<MessageResponse> deleteTask(@RequestParam("id") String id) {
        return service.deleteTaskById(Long.parseLong(id));
    }


    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PutMapping("/update_status")
    public ResponseEntity<MessageResponse> updateStatus(@RequestBody StatusRequest request) {
        return service.updateStatus(request);
    }


    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PutMapping("/update_task")
    public ResponseEntity<MessageResponse> updateTask(@RequestBody UpdateTaskRequest request) {
        return service.updateTask(request);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_member")
    public ResponseEntity<MessageResponse> deleteMember(@RequestParam("user_id") String userId, @RequestParam("task_id") String taskId) {
        return service.deleteMember(Long.parseLong(userId), Long.parseLong(taskId));
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PostMapping("/add_member")
    public ResponseEntity<MessageResponse> addMember(@RequestBody TaskUserRequest request){
        return service.addMember(request.getUser_id(), request.getTask_id());
    }

    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/update_executor")
    public ResponseEntity<MessageResponse> updateExecutor(@RequestBody TaskUserRequest request){
        return service.updateExecutor(request.getUser_id(), request.getTask_id());
    }
}
