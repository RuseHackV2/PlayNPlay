package com.ruse.hack.utils.di;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.ruse.hack.services.ModelMapperService;
import com.ruse.hack.services.ModelMapperServiceImpl;
import com.ruse.hack.services.TmdbService;
import com.ruse.hack.services.TmdbServiceImpl;

/**
 * Created by nslavov on 11/6/15.
 */
public class ServicesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TmdbService.class).to(TmdbServiceImpl.class).in(Singleton.class);
        bind(ModelMapperService.class).to(ModelMapperServiceImpl.class);

    }
}
