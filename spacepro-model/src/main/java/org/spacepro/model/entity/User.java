package org.spacepro.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User extends AbstractEntity{

    @Column(unique = true)
    private String loginName;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "creator")
    private List<Topic> topics;

    @OneToMany(mappedBy = "creator")
    private List<Comment> comments;

    public User(String loginName, String password){
        this.loginName = loginName;
        this.password = password;
    }
}
