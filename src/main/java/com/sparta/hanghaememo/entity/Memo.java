package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String users;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(nullable = false)
    private String titles;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String passwords;

    public Memo(MemoRequestDto requestDto){
        this.titles=requestDto.getTitles();
        this.contents=requestDto.getContents();
    }

    public void update(MemoRequestDto requestDto){
        this.titles=requestDto.getTitles();
        this.contents=requestDto.getContents();
    }


    public void confirmWriter(User writer) {
        //writer는 변경이 불가능하므로 이렇게만 해주어도 될듯
        this.writer = writer;
        writer.addMemo(this);
    }

    @Builder
    public Memo(String users, String titles, String contents) {
        this.users=users;
        this.titles = titles;
        this.contents = contents;
    }

}