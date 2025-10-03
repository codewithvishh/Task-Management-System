package in.vishal.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "task")
@Data
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
	@SequenceGenerator(name = "task_seq", sequenceName = "TASK_SEQ", allocationSize = 1)
	private Long id;


    private String name;
    @Column(name = "task_date")
    private LocalDate date;

    @Column(name = "task_time")
    private LocalTime time;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    // Getters and setters
}
