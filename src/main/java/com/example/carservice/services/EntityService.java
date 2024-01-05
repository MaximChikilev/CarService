package com.example.carservice.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public abstract class EntityService<T> {
    protected final JpaRepository<T,Long> repository;

    protected EntityService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }
    @Transactional(readOnly = true)
    public List<T> getAll(){
        return repository.findAll();
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
}
