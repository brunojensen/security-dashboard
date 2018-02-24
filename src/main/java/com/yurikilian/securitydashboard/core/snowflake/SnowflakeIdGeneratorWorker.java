package com.yurikilian.securitydashboard.core.snowflake;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import org.jboss.logging.Logger;

public class SnowflakeIdGeneratorWorker {

  protected static final Logger LOG = Logger.getLogger(SnowflakeIdGeneratorWorker.class);

  protected final long twepoch = 1288834974657L;

  private final long workerIdBits = 10L;
  private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
  private final long sequenceBits = 12L;

  private final long workerIdShift = sequenceBits;
  private final long timestampLeftShift = sequenceBits + workerIdBits;
  private final long sequenceMask = -1L ^ (-1L << sequenceBits);

  protected long workerId = 0;

  protected long sequence = 0L;
  private long lastTimestamp = -1L;

  {
    byte[] address;
    try {
      address = InetAddress.getLocalHost().getAddress();
    } catch (UnknownHostException e) {
      address = null;
    }
    if (address != null) {
      for (byte x : address) {
        workerId = ((workerId << 8) - Byte.MIN_VALUE + x) & maxWorkerId;
      }
    } else {
      LOG.warn("Cannot get ip address for generating server id, use random address instead.");
      workerId = new Random().nextLong() & maxWorkerId;
    }
    LOG.infof(
        "Worker starting. Timestamp left shift %d, worker id bits %d, sequence bits %d, worker id %d.",
        timestampLeftShift, workerIdBits, sequenceBits, workerId);
  }

  protected synchronized long nextId() {
    long timestamp = timeGen();

    if (timestamp < lastTimestamp) {
      LOG.errorf("Clock is moving backwards. Rejecting requests until %d.", lastTimestamp);
      throw new RuntimeException(
          String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
              lastTimestamp - timestamp));
    }

    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      if (sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0;
    }

    lastTimestamp = timestamp;
    return ((timestamp - twepoch) << timestampLeftShift) | (workerId << workerIdShift) | sequence;
  }

  protected long tilNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  protected long timeGen() {
    return System.currentTimeMillis();
  }

}
