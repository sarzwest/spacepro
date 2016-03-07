package org.spacepro.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Topic extends AbstractEntity {

    private String header;
    private String content;
    @ManyToOne
    private User creator;
    @OneToMany(mappedBy = "topic")
    private List<Comment> comments;

    public Topic() {
    }

    public Topic(String header, String content) {
        this.header = header;
        this.content = content;
    }
}
