package com.github.scova0731.petstore4s.step1

import javax.sql.DataSource

import play.api.Logger
import play.api.db.Database
import com.google.inject.Provides
import com.google.inject.name.Names
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory


/**
  * MyBatis DI module for Play
  *
  * Thanks to
  *   https://inoio.de/blog/2013/02/07/integrating-mybatis-guice-play2/
  *   https://www.innoq.com/en/blog/play24-guice-mybatis/
  */
class MyBatisModule extends org.mybatis.guice.MyBatisModule {

  override def initialize(): Unit = {
    environmentId("development")
    bindConstant.annotatedWith(Names.named("mybatis.configuration.failFast")).to(true)
    bindTransactionFactoryType(classOf[JdbcTransactionFactory])
    addMapperClasses("com.github.scova0731.petstore4s.step1.mapper")

    // Disable cache currently to escape from deserializing issue of case class
    // [PersistenceException:
    //   ### Error querying database.  Cause: org.apache.ibatis.cache.CacheException: Error deserializing object.  Cause: java.lang.ClassNotFoundException: Cannot find class: com.github.scova0731.petstore4s.step1.domain.Product
    //   ### Cause: org.apache.ibatis.cache.CacheException: Error deserializing object.  Cause: java.lang.ClassNotFoundException: Cannot find class: com.github.scova0731.petstore4s.step1.domain.Product]
    useCacheEnabled(false)

    Logger.info("MyBatisModule is initialized now.")
  }

  @Provides
  def provideDatabase(db: Database): DataSource =
    db.dataSource

}


