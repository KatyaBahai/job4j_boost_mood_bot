package ru.job4j.bmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.bmb.model.MoodLog;
import ru.job4j.bmb.model.User;

import java.util.List;

@Repository
public interface MoodLogRepository extends JpaRepository<MoodLog, Long> {
    List<MoodLog> findAllByUserIdAndCreationDateGreaterThan(Long userId, long dateMillis);

    @Query("""
            SELECT DISTINCT u FROM User u
            WHERE NOT EXISTS (
            SELECT 1 FROM MoodLog ml
            WHERE ml.user.id = u.id
            AND ml.createdAt >= :startOfDay
            AND ml.createdAt <= :endOfDay
            )""")
    List<User> findUsersWhoDidNotVoteToday(long startOfDay, long endOfDay);
}
