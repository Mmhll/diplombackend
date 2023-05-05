package com.mhl.mycompanybackend.controller;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.pojo.*;
import com.mhl.mycompanybackend.service.TasksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api( tags = "Clients")
public class TasksController {

    final TasksService service;

    public TasksController(TasksService service) {
        this.service = service;
    }


    @ApiOperation("dsada")
    @GetMapping("/get_all_tasks_executor")
    public ResponseEntity<?> getAllTasksExecutor(@RequestBody IdRequest request) {
        return service.getAllTasksWhereExecutor(request.getId());
    }

    @ApiOperation("dsada")
    @GetMapping("/get_all_tasks_creator")
    public ResponseEntity<?> getAllTasksCreator(@RequestBody IdRequest request) {
        return service.getAllTasksWhereCreator(request.getId());
    }

    @ApiOperation("dsada")
    @GetMapping("/get_all_tasks_member")
    public ResponseEntity<?> getAllTasksMember(@RequestBody IdRequest request) {
        return service.getAllTasksWhereMember(request.getId());
    }

    @ApiOperation("dsada")
    @GetMapping("/get_task")
    public ResponseEntity<?> getTask(@RequestBody Long id) {
        return service.getTask(id);
    }

    @ApiOperation("dsada")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PostMapping("/save_task")
    public ResponseEntity<?> saveTask(@RequestBody TaskRequest task) {
        return service.saveTask(task);
    }


    @ApiOperation("dsada")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_task")
    public ResponseEntity<MessageResponse> deleteTask(@RequestBody Long id) {
        return service.deleteTaskById(id);
    }


    @ApiOperation("dsada")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PutMapping("/update_status")
    public ResponseEntity<MessageResponse> updateStatus(@RequestBody StatusRequest request) {
        return service.updateStatus(request);
    }


    @ApiOperation("dsada")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PutMapping("/update_task")
    public ResponseEntity<MessageResponse> updateTask(@RequestBody UpdateTaskRequest request) {
        return service.updateTask(request);
    }

    @ApiOperation("dsada")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_member")
    public ResponseEntity<MessageResponse> deleteMember(@RequestBody TaskUserRequest request) {
        return service.deleteMember(request.getUser_id(), request.getTask_id());
    }

    @ApiOperation("dsada")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PostMapping("/add_member")
    public ResponseEntity<MessageResponse> addMember(@RequestBody TaskUserRequest request){
        return service.addMember(request.getUser_id(), request.getTask_id());
    }

    @ApiOperation("dsada")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/update_executor")
    public ResponseEntity<MessageResponse> updateExecutor(@RequestBody TaskUserRequest request){
        return service.updateExecutor(request.getUser_id(), request.getTask_id());
    }
}
