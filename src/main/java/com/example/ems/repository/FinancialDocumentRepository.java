package com.example.ems.repository;

import com.example.ems.entity.FinancialDocument;
import com.example.ems.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FinancialDocumentRepository extends JpaRepository<FinancialDocument, UUID> {

	List<FinancialDocument> findByUser(Users user);
	void deleteByUser(Users user);

}
