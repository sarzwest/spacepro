package org.spacepro.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, insertable = true, updatable = false)
    private Date creationTime;

    private Date modifiedTime;

    @PrePersist
    void setCreationTime(){
        Date now = new Date();
        creationTime = now;
        modifiedTime = now;
    }

    @PreUpdate
    void setModifiedTime(){
        creationTime = new Date();
    }
}
