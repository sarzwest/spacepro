package org.spacepro.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Data
@Entity
public class Comment extends AbstractEntity{

    @ManyToOne
    private User creator;
    @ManyToOne
    private Topic topic;
    @Column(nullable = false)
    private String content;
}
