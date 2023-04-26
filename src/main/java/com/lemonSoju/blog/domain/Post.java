package com.lemonSoju.blog.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    @Lob
    private String content;

    private LocalDateTime createDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Member writer;

    @OneToMany(mappedBy = "heart")
    private List<Member> hearts = new ArrayList<>();

    @Builder
    public Post(String title, String content, Member writer, LocalDateTime createDate){
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createDate = createDate;
    }
}