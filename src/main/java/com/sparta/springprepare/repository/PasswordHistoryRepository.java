package com.sparta.springprepare.repository;

import com.sparta.springprepare.domain.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {
}
