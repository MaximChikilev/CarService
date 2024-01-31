package com.example.carservice.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public abstract class EntityService<T> {
    protected final JpaRepository<T,Long> repository;
    protected Map<String, Function<String, List<T>>> methodMap = new HashMap<>();

    protected EntityService(JpaRepository<T, Long> repository) {
        this.repository = repository;
        setSearchFieldsAndCorrespondingMethods();
    }
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
    @Transactional(readOnly = true)
    public List<T> getAll(){
        return repository.findAll();
    }
    @Transactional(readOnly = true)
    public List<T> getAll(Pageable pageable){
        return repository.findAll(pageable).getContent();
    }
    @Transactional
    public void save(T entity){
        repository.save(entity);
    }
    @Transactional
    public T getById(Long id){
        return repository.findById(id).orElse(null);
    }
    @Transactional
    public void delete(Long id){
        repository.deleteById(id);
    }
    @Transactional
    public void saveAll(List<T> list){
        repository.saveAll(list);
    }
    @Transactional
    public List<T> getAllByFieldNameAndValue(String fieldName, String value) {
        return methodMap.get(fieldName).apply(value);
    }
    protected abstract Map<String, Function<String, List<T>>> setSearchFieldsAndCorrespondingMethods();
    protected abstract List<T> loadEntityListFromJson() throws IOException;
}
