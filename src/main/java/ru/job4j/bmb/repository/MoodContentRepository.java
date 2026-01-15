package ru.job4j.bmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.MoodContent;

import java.util.Optional;

@Repository
public interface MoodContentRepository extends JpaRepository<MoodContent, Long> {
    Optional<MoodContent> findByMoodId(long moodId);
}
