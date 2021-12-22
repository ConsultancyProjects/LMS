package com.lms.tutor.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.tutor.model.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Integer> {

	List<TimeTable> findAllByFromDate(Timestamp fromDate);

}
