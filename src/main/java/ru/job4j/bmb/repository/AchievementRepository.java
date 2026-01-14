package ru.job4j.bmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.Achievement;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository  extends JpaRepository<Achievement, Long> {
    List<Achievement> findAllByUserId(Long userId);
}
