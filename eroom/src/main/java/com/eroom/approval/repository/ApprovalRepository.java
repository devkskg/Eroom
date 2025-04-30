package com.eroom.approval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eroom.approval.entity.Approval;
import com.eroom.approval.entity.ApprovalLine;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {

	List<Approval> findByEmployee_EmployeeNoAndApprovalVisibleYnOrderByApprovalRegDateDesc(Long employeeNo, String visible);

	List<Approval> findByApprovalNoAndApprovalVisibleYnOrderByApprovalRegDateDesc(Long approvalNo, String visible);

	List<Approval> findAllByApprovalVisibleYn(String string);


	
}
