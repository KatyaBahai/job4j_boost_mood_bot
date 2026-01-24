package ru.job4j.bmb.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mb_user", uniqueConstraints = @UniqueConstraint(columnNames = "clientId"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", unique = true, nullable = false)
    private long clientId;

    @Column(name = "chat_id")
    private long chatId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
