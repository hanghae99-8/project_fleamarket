package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.CommentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;


    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;


    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="memo_id")
    private Memo memo;


    @Column
    private String content;

    @Column
    private String username;

    /* 연관관계 편의 메서드 */
    public void confirmWriter(User writer) {
        this.writer = writer;
        writer.addComment(this);
    }

    public void confirmMemo(Memo memo) {
        this.memo = memo;
        memo.addComment(this);
    }

    public void updateComment(CommentRequest commentRequest) {
        this.content= commentRequest.getContent();
    }
    @Builder
    public Comment( User writer, Memo memo, String username, String content) {
        this.writer = writer;
        this.memo = memo;
        this.username = username;
        this.content = content;

    }
}