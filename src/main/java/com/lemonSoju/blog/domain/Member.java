package com.lemonSoju.blog.domain;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String uid;
    private String pwd;
    private String name;
    private String authority;

    @OneToMany(mappedBy = "writer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Like> likes = new ArrayList<>();

    @Builder
    public Member(String uid, String pwd, String name, String authority) {
        this.uid = uid;
        this.pwd = pwd;
        this.name = name;
        this.authority = authority;
    }

    protected Member() {
    }
}