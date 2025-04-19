package com.energy.usage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface UsageRepository extends JpaRepository<UsageEntry, LocalDateTime> {
}