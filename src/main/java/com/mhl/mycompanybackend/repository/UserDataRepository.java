package com.mhl.mycompanybackend.repository;

import com.mhl.mycompanybackend.model.UserData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserDataRepository extends CrudRepository<UserData, Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE user_data " +
            "SET firstname = ?1, lastname = ?2, middlename = ?3, phone_number = ?4 " +
            "WHERE id = ?5")
    void updateUser(String firstname, String lastname, String middlename, String phone_number, Long id);

}
