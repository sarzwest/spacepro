package org.spacepro.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {

    private String content;
    private Date creationTime;
    private String author;
}
