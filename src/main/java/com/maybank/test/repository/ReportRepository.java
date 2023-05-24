package com.maybank.test.repository;

import com.maybank.test.model.HistoryExport;
import com.maybank.test.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByFilename(String filename);
}
