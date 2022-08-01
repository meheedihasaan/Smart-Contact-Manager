package com.contact.manager.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.manager.entites.Contact;
import com.contact.manager.entites.User;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	@Query("FROM Contact as c WHERE c.user.id = :userId")
	//Pageable has two properties - current page, contacts per page
	public Page<Contact> getContactByUser(@Param("userId") int userId, Pageable pageable);

	//For Searching Contact
	public List<Contact> findByNameContainingAndUser(String name, User user);
	
}
