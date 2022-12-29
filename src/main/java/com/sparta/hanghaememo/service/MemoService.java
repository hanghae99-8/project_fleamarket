package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
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
    public MemoResponseDto createMemo(MemoRequestDto memorequestDto){
        Memo memo = new Memo(memorequestDto);
        memoRepository.save(memo);
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo.getCreatedAt(),memo.getModifiedAt(),memo.getUsers(),memo.getTitles(),memo.getContents());
        return memoResponseDto;
    }

    @Transactional(readOnly = true)
    public List<Memo> getMemo() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public MemoResponseDto getDetail(Long id){
        Memo memo = checkMemo(memoRepository,id);
        return new MemoResponseDto(memo.getCreatedAt(),memo.getModifiedAt(),memo.getUsers(),memo.getTitles(),memo.getContents());
    }


    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findByIdAndPasswords(id,requestDto.getPasswords());
        if(!memo.equals(null)) {
            memo.update(requestDto);
        }
        else if(memo.equals(null)){
            return null;
        }
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo.getCreatedAt(),memo.getModifiedAt(),memo.getUsers(),memo.getTitles(),memo.getContents());
        return memoResponseDto;
    }

    @Transactional
    public String deleteMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findByIdAndPasswords(id,requestDto.getPasswords());
        if(memo!=null) {
            memoRepository.deleteById(id);
            return "\"success\":true";
        }
        else{
            return "\"success\":false";
        }

    }

    private Memo checkMemo(MemoRepository memoRepository,Long id){
        return memoRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("게시글일 존재하지 않습니다.")
        );
    }

    private static boolean checkPasswords(MemoRequestDto memoRequestDto,Memo memo){
        return memo.getPasswords().equals(memoRequestDto.getPasswords());
    }

}
