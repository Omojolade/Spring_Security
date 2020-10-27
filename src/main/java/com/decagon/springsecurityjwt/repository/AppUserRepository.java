package com.decagon.springsecurityjwt.repository;

import com.decagon.springsecurityjwt.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Finds a user by email when the user is enabled
     *
     * @param email user email
     * @return <AppUser>
     */
    @Query(name = "select * from app_user where app_user.email =:email and app_user.enabled = true",
            nativeQuery = true)
    Optional<AppUser> findByEmail(@Param("email") String email);

}
