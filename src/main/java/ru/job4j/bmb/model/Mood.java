package ru.job4j.bmb.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "mb_mood")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String text;

    private boolean good;
}
