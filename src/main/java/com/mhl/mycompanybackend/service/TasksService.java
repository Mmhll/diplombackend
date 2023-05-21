package com.mhl.mycompanybackend.service;

import com.mhl.mycompanybackend.model.Tasks;
import com.mhl.mycompanybackend.model.Users;
import com.mhl.mycompanybackend.pojo.*;
import com.mhl.mycompanybackend.repository.TasksRepository;
import com.mhl.mycompanybackend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class TasksService {
    final TasksRepository tasksRepository;
    final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAllTasksWhereExecutor(Long id) {
        try {
            Users executor = userRepository.findById(id).get();
            return ResponseEntity.ok().body(tasksRepository.findAllByExecutorEquals(executor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

    public ResponseEntity<?> getAllTasksWhereCreator(Long id) {
        try {
            Users creator = userRepository.findById(id).get();
            return ResponseEntity.ok().body(tasksRepository.findAllByCreatorEquals(creator));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

    public ResponseEntity<?> getAllTasksWhereMember(Long id) {
        try {
            Users member = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok().body(tasksRepository.findAllByMembersContains(member));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

    public ResponseEntity<?> getTask(Long id) {
        try {
            return ResponseEntity.ok().body(tasksRepository.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> saveTask(TaskRequest taskRequest) {
        ArrayList<Users> members = new ArrayList<>();
        taskRequest.getMembers().forEach(member -> members.add(userRepository.findById(member).get()));
        Tasks task = new Tasks(
                taskRequest.getTask_name(),
                userRepository.findById(taskRequest.getCreator_id()).get(),
                taskRequest.getCreation_date(),
                taskRequest.getDescription(),
                taskRequest.getDeadline(),
                userRepository.findById(taskRequest.getExecutor_id()).get(),
                "Новая",
                members
        );
        tasksRepository.save(task);
        return ResponseEntity.ok().body(new MessageResponse("Task was saved"));
    }

    public ResponseEntity<MessageResponse> deleteTaskById(Long id) {
        try {
            Tasks task = tasksRepository.findById(id).get();
            tasksRepository.deleteTasksUsersById(task.getId());
            tasksRepository.deleteTasksById(task.getId());
            return ResponseEntity.ok().body(new MessageResponse("Task was deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> updateStatus(StatusRequest request) {
        try {
            tasksRepository.updateStatus(request.getId(), request.getStatus());
            return ResponseEntity.ok().body(new MessageResponse("Task was updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> updateTask(UpdateTaskRequest request) {
        try {
            tasksRepository.updateTask(request.getTask_id(), request.getName(), request.getDescription(), request.getDeadline());
            return ResponseEntity.ok().body(new MessageResponse("Task was updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task not found or something went wrong"));
        }
    }

    public ResponseEntity<MessageResponse> addMember(Long member_id, Long task_id) {
        try {
            Tasks task = tasksRepository.findById(task_id).get();
            boolean exists = false;
            if (!task.getMembers().isEmpty()) {
                for (int i = 0; i < task.getMembers().size(); i++) {
                    if (Objects.equals(task.getMembers().get(i).getId(), member_id)) {
                        exists = true;
                        break;
                    }
                }
            }
            if (exists) {
                return ResponseEntity.badRequest().body(new MessageResponse("User already is member of task"));
            } else {
                try {
                    userRepository.findById(member_id).get();
                    tasksRepository.addMemberToTask(task_id, member_id);
                    return ResponseEntity.ok().body(new MessageResponse("User was added to task"));
                } catch (Exception ex){
                    return ResponseEntity.badRequest().body(new MessageResponse("User doesn't exists"));

                }
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Task doesn't exists"));
        }

    }


    public ResponseEntity<MessageResponse> deleteMember(Long memberId, Long taskId) {
        try {
            Tasks task = tasksRepository.findById(taskId).get();
            boolean exists = false;
            if (!task.getMembers().isEmpty()) {
                for (int i = 0; i < task.getMembers().size(); i++) {
                    if (Objects.equals(task.getMembers().get(i).getId(), memberId)) {
                        exists = true;
                        break;
                    }
                }
            }
            if (exists) {
                tasksRepository.deleteMemberFromTask(memberId, taskId);
                return ResponseEntity.ok().body(new MessageResponse("User was deleted from task"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("User doesn't exists"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Task doesn't exists"));
        }
    }

    public ResponseEntity<MessageResponse> updateExecutor(Long executor_id, Long task_id) {
        try {
            tasksRepository.updateExecutor(executor_id, task_id);
            return ResponseEntity.ok().body(new MessageResponse("Executor was updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User or task doesn't exists"));
        }
    }


}
