package com.example.carservice.repo;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>  {
    @Query("SELECT u FROM User u where u.login = :login")
    User findByLogin(@Param("login") String login);
    @Query("SELECT s FROM User s WHERE s.login LIKE %:value%")
    List<User> findAllByUserRoleContaining(@Param("value") String value);
    @Query("SELECT u FROM User u where u.login = :login OR u.email = :email")
    User findByLoginOrEmail(@Param("login") String login,@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);
}
