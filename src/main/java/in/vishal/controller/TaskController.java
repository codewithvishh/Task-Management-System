package in.vishal.controller;

import in.vishal.model.Task;
import in.vishal.model.User;
import in.vishal.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // View all tasks
    @GetMapping
    public String viewTasks(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return "redirect:/login";
        }
        model.addAttribute("tasks", taskService.getTasksByUser(user));
        return "tasks"; // templates/tasks.html
    }

    // Show create form
    @GetMapping("/create")
    public String showCreateForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return "redirect:/login";
        }
        model.addAttribute("task", new Task());
        return "create-task"; // templates/create-task.html
    }

    // Handle create form
    @PostMapping("/create")
    public String createTask(@ModelAttribute("task") @Valid Task task,
                             BindingResult result,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            return "create-task";
        }
        task.setUser(user);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return "redirect:/login";
        }

        Task task = taskService.getTaskById(id);
        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        return "edit-task"; // templates/edit-task.html
    }

    // Handle edit form
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id,
                             @ModelAttribute("task") @Valid Task task,
                             BindingResult result,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            return "edit-task";
        }

        Task existingTask = taskService.getTaskById(id);
        if (existingTask == null || !existingTask.getUser().getId().equals(user.getId())) {
            return "redirect:/tasks";
        }

        existingTask.setName(task.getName());
        existingTask.setDate(task.getDate());
        existingTask.setTime(task.getTime());

        taskService.updateTask(existingTask);
        return "redirect:/tasks";
    }

    // Delete task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == null) {
            return "redirect:/login";
        }

        Task task = taskService.getTaskById(id);
        if (task != null && task.getUser().getId().equals(user.getId())) {
            taskService.deleteTask(task);
        }

        return "redirect:/tasks";
    }
}
