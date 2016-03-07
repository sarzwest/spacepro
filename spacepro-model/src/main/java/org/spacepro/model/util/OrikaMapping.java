package org.spacepro.model.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaMapping {

    public MapperFacade mapper(){
        MapperFactory mf = new DefaultMapperFactory.Builder().mapNulls(false).build();
        MapperFacade facade = mf.getMapperFacade();
        return facade;
    }
}
