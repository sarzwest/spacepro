package org.spacepro.rest.util;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.spacepro.model.entity.Topic;
import org.spacepro.rest.vo.TopicVO;

public class Mapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.classMap(TopicVO.class, Topic.class)
                .byDefault()
                .register();
    }
}
