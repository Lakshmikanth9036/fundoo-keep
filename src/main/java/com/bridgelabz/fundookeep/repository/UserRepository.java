package com.bridgelabz.fundookeep.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundookeep.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Modifying
	@Transactional
	@Query("update User u set u.isUserVerified=1 where u.id=:id")
	int updateUserVerificationStatus(@Param("id") Long id);

	User findByEmailAddressOrMobile(String email,Long mobile);

	User findByEmailAddress(String emailAddress);

	@Modifying
	@Transactional
	@Query("update User u set u.password=:newPassword where u.id=:id")
	int updatePassword(@Param("id") Long id,@Param("newPassword") String newPassword);

}
