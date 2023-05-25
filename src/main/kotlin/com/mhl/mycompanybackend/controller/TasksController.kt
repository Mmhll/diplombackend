package com.mhl.mycompanybackend.controller

import com.mhl.mycompanybackend.pojo.*
import com.mhl.mycompanybackend.service.TasksService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = ["*"], maxAge = 3600)
@Api(tags = ["Tasks"])
@ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
class TasksController(val service: TasksService) {
    @GetMapping("/get_all_tasks_executor")
    fun getAllTasksExecutor(@RequestParam id: String): ResponseEntity<*> {
        return service.getAllTasksWhereExecutor(id.toLong())
    }

    @GetMapping("/get_all_tasks_creator")
    fun getAllTasksCreator(@RequestParam id: String): ResponseEntity<*> {
        return service.getAllTasksWhereCreator(id.toLong())
    }

    @GetMapping("/get_all_tasks_member")
    fun getAllTasksMember(@RequestParam id: String): ResponseEntity<*> {
        return service.getAllTasksWhereMember(id.toLong())
    }

    @GetMapping("/get_task")
    fun getTask(@RequestParam id: String): ResponseEntity<*> {
        return service.getTask(id.toLong())
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PostMapping("/save_task")
    fun saveTask(@RequestBody task: TaskRequest?): ResponseEntity<*> {
        return service.saveTask(task!!)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_task")
    fun deleteTask(@RequestParam("id") id: String): ResponseEntity<MessageResponse> {
        return service.deleteTaskById(id.toLong())
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PutMapping("/update_status")
    fun updateStatus(@RequestBody request: StatusRequest?): ResponseEntity<MessageResponse> {
        return service.updateStatus(request!!)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PutMapping("/update_task")
    fun updateTask(@RequestBody request: UpdateTaskRequest?): ResponseEntity<MessageResponse> {
        return service.updateTask(request!!)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete_member")
    fun deleteMember(@RequestParam("user_id") userId: String, @RequestParam("task_id") taskId: String): ResponseEntity<MessageResponse> {
        return service.deleteMember(userId.toLong(), taskId.toLong())
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'EDITUSER')")
    @PostMapping("/add_member")
    fun addMember(@RequestBody request: TaskUserRequest): ResponseEntity<MessageResponse> {
        return service.addMember(request.user_id!!, request.task_id!!)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/update_executor")
    fun updateExecutor(@RequestBody request: TaskUserRequest): ResponseEntity<MessageResponse> {
        return service.updateExecutor(request.user_id, request.task_id)
    }
}
