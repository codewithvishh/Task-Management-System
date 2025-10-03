package in.vishal.service;

import in.vishal.model.*;
import in.vishal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    public void createTask(Task task) {
        taskRepo.save(task);
    }

    public List<Task> getTasksByUser(User user) {
        return taskRepo.findByUser(user);
    }

    public Task getTaskById(Long id) {
        return taskRepo.findById(id).orElse(null);
    }

    public void updateTask(Task task) {
        taskRepo.save(task);
    }

    public void deleteTask(Task task) {
        taskRepo.delete(task);
    }
}
