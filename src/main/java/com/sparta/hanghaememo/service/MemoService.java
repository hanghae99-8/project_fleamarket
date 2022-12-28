package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    @Transactional
    public Memo createMemo(MemoRequestDto requestDto){
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);

        return memo;
    }

    @Transactional(readOnly = true)
    public List<Memo> getMemo() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        memo.update(requestDto);
        return memo.getId();
    }

    @Transactional
    public Long deleteMemo(Long id) {
        memoRepository.deleteById(id);
        return id;
    }

/*    public MemoRequestDto getDetail(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        return new MemoRequestDto(memo);
    }*/
}
