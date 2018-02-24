package com.yurikilian.securitydashboard.core.snowflake;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SnowflakeIdGenerator implements IdentifierGenerator {

  private SnowflakeIdGeneratorWorker worker = new SnowflakeIdGeneratorWorker();

  @Override
  public Serializable generate(SessionImplementor session, Object object)
      throws HibernateException {
    return worker.nextId();
  }


}
