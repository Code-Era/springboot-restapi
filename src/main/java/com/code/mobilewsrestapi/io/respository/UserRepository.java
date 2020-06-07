package com.code.mobilewsrestapi.io.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.mobilewsrestapi.io.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	public UserEntity findByUserId(String userID);
	public UserEntity findByUsername(String username);
	public UserEntity findBySsn(String ssn);
	public void deleteByUserId(String userID);


}
