package ru.job4j.bmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.Mood;

import java.util.List;

@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> {
}
