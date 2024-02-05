package com.example.carservice.repo;

import com.example.carservice.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<CustomUser,Long>  {
    @Query("SELECT u FROM CustomUser u where u.login = :login")
    CustomUser findByLogin(@Param("login") String login);
    @Query("SELECT s FROM CustomUser s WHERE s.login LIKE %:value%")
    List<CustomUser> findAllByUserRoleContaining(@Param("value") String value);
    @Query("SELECT u FROM CustomUser u where u.login = :login OR u.email = :email")
    CustomUser findByLoginOrEmail(@Param("login") String login, @Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);
}
