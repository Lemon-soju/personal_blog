package com.lemonSoju.blog.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String uid;
    private String pwd;
    private String name;
    private String authority;

    @OneToMany(mappedBy = "writer")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post heart;

    @Builder
    public Member(String uid, String pwd, String name, String authority){
        this.uid = uid;
        this.pwd = pwd;
        this.name = name;
        this.authority = authority;
    }
}