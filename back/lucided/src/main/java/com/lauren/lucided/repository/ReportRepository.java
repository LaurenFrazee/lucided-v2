package com.lauren.lucided.repository;

import com.lauren.lucided.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}