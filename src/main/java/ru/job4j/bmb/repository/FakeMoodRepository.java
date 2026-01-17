package ru.job4j.bmb.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import ru.job4j.bmb.model.Mood;

import java.util.*;
import java.util.function.Function;

public class FakeMoodRepository implements MoodRepository {
    private final Map<Long, Mood> moodMap = new HashMap<>();

    @Override
    public List<Mood> findAll() {
        return new ArrayList<>(moodMap.values());
    }

    public void add(Mood mood) {
        moodMap.put(mood.getId(), mood);
    }

    @Override
    public <S extends Mood> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Mood> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Mood> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Mood> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Mood entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Mood> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<Mood> findByText(String moodText) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Mood> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Mood> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Mood> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Mood getOne(Long aLong) {
        return null;
    }

    @Override
    public Mood getById(Long aLong) {
        return null;
    }

    @Override
    public Mood getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Mood> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Mood> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Mood> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Mood> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Mood> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Mood> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Mood, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Mood> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Mood> findAll(Pageable pageable) {
        return null;
    }
}
