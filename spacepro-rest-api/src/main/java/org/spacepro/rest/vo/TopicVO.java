package org.spacepro.rest.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicVO {

    private Long id;
    @JsonProperty("hedka")
    private String header;
    private String content;
    private Date creationTime;
    private String loginName;
}
