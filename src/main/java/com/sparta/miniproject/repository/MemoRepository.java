package com.sparta.miniproject.repository;


import com.sparta.miniproject.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();

    Memo findByIdAndPasswords(Long id, String passwords);
}