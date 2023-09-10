package com.lemonSoju.blog.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /**
     * 편의 관계 메서드
     */
    public void addMemberAndPost(Post post, Member member) {
        this.post = post;
        this.member = member;
        post.getLikes().add(this);
        member.getLikes().add(this);
    }

    @Builder
    public Like(Post post, Member member) {
        this.post = post;
        this.member = member;
    }

    protected Like() {
    }
}
