package com.anurag.repository;

import com.anurag.model.Massage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MassageRepository extends JpaRepository<Massage,Long> {

        List<Massage> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
