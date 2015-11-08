package com.ruse.hack.utils.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by nslavov on 11/6/15.
 */
public class ModuleDependencies extends AbstractModule {

    @Override
    protected void configure() {
    install(new DaoModule());

    }

    @Singleton
    @Provides
    HttpClient providesHttpClient() {
        return HttpClients.createDefault();
    }

}
