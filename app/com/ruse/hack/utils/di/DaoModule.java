package com.ruse.hack.utils.di;

import com.google.inject.AbstractModule;
import com.ruse.hack.dao.BaseDao;
import com.ruse.hack.dao.BaseDaoImpl;
import com.ruse.hack.dao.MovieDao;
import com.ruse.hack.dao.MovieDaoImpl;

/**
 * Created by nslavov on 11/6/15.
 */
public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BaseDao.class).to(BaseDaoImpl.class);
        bind(MovieDao.class).to(MovieDaoImpl.class);
    }
}
