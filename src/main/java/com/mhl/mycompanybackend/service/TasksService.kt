package com.mhl.mycompanybackend.service

import com.mhl.mycompanybackend.model.Tasks
import com.mhl.mycompanybackend.model.Users
import com.mhl.mycompanybackend.pojo.MessageResponse
import com.mhl.mycompanybackend.pojo.StatusRequest
import com.mhl.mycompanybackend.pojo.TaskRequest
import com.mhl.mycompanybackend.pojo.UpdateTaskRequest
import com.mhl.mycompanybackend.repository.TasksRepository
import com.mhl.mycompanybackend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.function.Consumer

@Service
class TasksService(val tasksRepository: TasksRepository, val userRepository: UserRepository) {
    fun getAllTasksWhereExecutor(id: Long): ResponseEntity<*> {
        return try {
            val executor = userRepository.findById(id).get()
            ResponseEntity.ok().body(tasksRepository.findAllByExecutorEquals(executor))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found"))
        }
    }

    fun getAllTasksWhereCreator(id: Long): ResponseEntity<*> {
        return try {
            val creator = userRepository.findById(id).get()
            ResponseEntity.ok().body(tasksRepository.findAllByCreatorEquals(creator))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found"))
        }
    }

    fun getAllTasksWhereMember(id: Long): ResponseEntity<*> {
        return try {
            val member = userRepository.findById(id).orElseThrow { RuntimeException("User not found") }!!
            ResponseEntity.ok().body(tasksRepository.findAllByMembersContains(member))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User not found"))
        }
    }

    fun getTask(id: Long): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(tasksRepository.findById(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Task not found or something went wrong"))
        }
    }

    fun saveTask(taskRequest: TaskRequest): ResponseEntity<MessageResponse> {
        val members = ArrayList<Users>()
        taskRequest.members!!.forEach(Consumer { member: Long -> members.add(userRepository.findById(member).get()) })
        val task = Tasks(
                taskRequest.task_name,
                userRepository.findById(taskRequest.creator_id).get(),
                taskRequest.creation_date,
                taskRequest.description,
                taskRequest.deadline,
                userRepository.findById(taskRequest.executor_id).get(),
                "Новая",
                members
        )
        tasksRepository.save(task)
        return ResponseEntity.ok().body(MessageResponse("Task was saved"))
    }

    fun deleteTaskById(id: Long): ResponseEntity<MessageResponse> {
        return try {
            val task = tasksRepository.findById(id).get()
            tasksRepository.deleteTasksUsersById(task.id)
            tasksRepository.deleteTasksById(task.id)
            ResponseEntity.ok().body(MessageResponse("Task was deleted"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Task not found or something went wrong"))
        }
    }

    fun updateStatus(request: StatusRequest): ResponseEntity<MessageResponse> {
        return try {
            tasksRepository.updateStatus(request.id, request.status)
            ResponseEntity.ok().body(MessageResponse("Task was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Task not found or something went wrong"))
        }
    }

    fun updateTask(request: UpdateTaskRequest): ResponseEntity<MessageResponse> {
        return try {
            tasksRepository.updateTask(request.task_id, request.name, request.description, request.deadline)
            ResponseEntity.ok().body(MessageResponse("Task was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Task not found or something went wrong"))
        }
    }

    fun addMember(member_id: Long, task_id: Long): ResponseEntity<MessageResponse> {
        return try {
            val task = tasksRepository.findById(task_id).get()
            var exists = false
            if (!task.members.isEmpty()) {
                for (i in task.members.indices) {
                    if (task.members[i].id == member_id) {
                        exists = true
                        break
                    }
                }
            }
            if (exists) {
                ResponseEntity.badRequest().body(MessageResponse("User already is member of task"))
            } else {
                try {
                    userRepository.findById(member_id).get()
                    tasksRepository.addMemberToTask(task_id, member_id)
                    ResponseEntity.ok().body(MessageResponse("User was added to task"))
                } catch (ex: Exception) {
                    ResponseEntity.badRequest().body(MessageResponse("User doesn't exists"))
                }
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Task doesn't exists"))
        }
    }

    fun deleteMember(memberId: Long?, taskId: Long): ResponseEntity<MessageResponse> {
        return try {
            val task = tasksRepository.findById(taskId).get()
            var exists = false
            if (!task.members.isEmpty()) {
                for (i in task.members.indices) {
                    if (task.members[i].id == memberId) {
                        exists = true
                        break
                    }
                }
            }
            if (exists) {
                tasksRepository.deleteMemberFromTask(memberId, taskId)
                return ResponseEntity.ok().body(MessageResponse("User was deleted from task"))
            }
            ResponseEntity.badRequest().body(MessageResponse("User doesn't exists"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("Task doesn't exists"))
        }
    }

    fun updateExecutor(executor_id: Long?, task_id: Long?): ResponseEntity<MessageResponse> {
        return try {
            tasksRepository.updateExecutor(executor_id, task_id)
            ResponseEntity.ok().body(MessageResponse("Executor was updated"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(MessageResponse("User or task doesn't exists"))
        }
    }
}
