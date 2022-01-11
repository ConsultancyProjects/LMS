package com.lms.tutor.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

	List<TimeTable> findAllByFromDate(Timestamp fromDate);
	
	List<TimeTable> findByBatchBatchId(int batchId);
	
	@Query(value = "SELECT * FROM lms.time_table where TO_DATE <= sysdate() and batch_id=:batchId", nativeQuery=true)
	List<TimeTable> findAllHistoryDataForBatch(@Param("batchId") int batchId);
	
	 @Query(value = "SELECT * FROM lms.time_table where BATCH_ID in (:batchId)", nativeQuery=true)
	 List<TimeTable> getTimeTableForUser(@Param("batchId")List<Integer> batchId);

}
