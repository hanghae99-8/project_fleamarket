package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String users;

    @Column(nullable = false)
    private String titles;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String passwords;

    public Memo(MemoRequestDto requestDto){
        this.users=requestDto.getUsers();
        this.titles=requestDto.getTitles();
        this.contents=requestDto.getContents();
        this.passwords=requestDto.getPasswords();
    }

    public void update(MemoRequestDto requestDto){
        this.users=requestDto.getUsers();
        this.titles=requestDto.getTitles();
        this.contents=requestDto.getContents();
        this.passwords=requestDto.getPasswords();
    }
}