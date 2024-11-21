package com.careerstreet.user_service.repository;

import com.careerstreet.user_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account,String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query("SELECT a.role.roleId FROM Account a WHERE a.username = :username")
    Long findRoleIdByUsername(@Param("username") String username);

    @Query("SELECT a.email FROM Account a WHERE a.username = :username")
    String findEmailByUsername(@Param("username") String username);
    Account findAccountByUsername(String username);
    @Modifying
    @Query("UPDATE Account a SET a.active = TRUE WHERE a.username = :username")
    void updateIsActiveByUsername(@Param("username") String username);

}
