package com.maybank.test.repository;

import com.maybank.test.dto.response.SuccessResponse;
import com.maybank.test.model.HistoryExport;
import com.maybank.test.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryExportRepository extends JpaRepository<HistoryExport, Long> {
    List<HistoryExport> findByReport(Report report);
}
