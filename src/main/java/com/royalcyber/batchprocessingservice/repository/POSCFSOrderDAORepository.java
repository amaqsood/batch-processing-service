package com.royalcyber.batchprocessingservice.repository;

import com.royalcyber.batchprocessingservice.entity.POSCFSOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POSCFSOrderDAORepository extends JpaRepository<POSCFSOrder, Integer> {
}
