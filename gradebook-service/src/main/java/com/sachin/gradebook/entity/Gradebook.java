package com.sachin.gradebook.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "gradebooks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gradebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long courseId;

    private double totalEarned;

    private double totalPossible;

    private double percentage;

    private String letterGrade;
}
