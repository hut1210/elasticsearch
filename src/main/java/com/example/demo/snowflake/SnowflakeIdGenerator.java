package com.example.demo.snowflake;

import java.util.Date;

/**
 * @author hut
 * @date 2022/9/29 5:01 下午
 */
public class SnowflakeIdGenerator {
    public static final int TOTAL_BITS = 1 << 6;

    private static final long SIGN_BITS = 1;

    private static final long TIME_STAMP_BITS = 41L;

    private static final long DATA_CENTER_ID_BITS = 5L;

    private static final long WORKER_ID_BITS = 5L;

    private static final long SEQUENCE_BITS = 12L;

    /**
     * 时间向左位移位数 22位
     */
    private static final long TIMESTAMP_LEFT_SHIFT = WORKER_ID_BITS + DATA_CENTER_ID_BITS + SEQUENCE_BITS;

    /**
     * IDC向左位移位数 17位
     */
    private static final long DATA_CENTER_ID_SHIFT = WORKER_ID_BITS + SEQUENCE_BITS;

    /**
     * 机器ID 向左位移位数 12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 序列掩码，用于限定序列最大值为4095
     */
    private static final long SEQUENCE_MASK =  -1L ^ (-1L << SEQUENCE_BITS);

    /**
     * 最大支持机器节点数0~31，一共32个
     */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    /**
     * 最大支持数据中心节点数0~31，一共32个
     */
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    /**
     * 最大时间戳 2199023255551
     */
    private static final long MAX_DELTA_TIMESTAMP = -1L ^ (-1L << TIME_STAMP_BITS);

    /**
     * Customer epoch
     */
    private final long twepoch;

    private final long workerId;

    private final long dataCenterId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    /**
     *
     * @param workerId 机器ID
     * @param dataCenterId  IDC ID
     */
    public SnowflakeIdGenerator(long workerId, long dataCenterId) {
        this(workerId, dataCenterId, null);
    }

    /**
     *
     * @param workerId  机器ID
     * @param dataCenterId IDC ID
     * @param epochDate 初始化时间起点
     */
    public SnowflakeIdGenerator(long workerId, long dataCenterId, Date epochDate) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than "+ MAX_WORKER_ID + " or less than 0");
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than {" + MAX_DATA_CENTER_ID + "} or less than 0");
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
        if (epochDate != null) {
            this.twepoch = epochDate.getTime();
        } else {
            //2010-10-11
            this.twepoch = 1286726400000L;
        }

    }

    public long genID() throws Exception {
        try {
            return nextId();
        } catch (Exception e) {
            throw e;
        }
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    /**
     * 通过移位解析出sequence，sequence有效位为[0,12]
     * 所以先向左移64-12，然后再像右移64-12，通过两次移位就可以把无效位移除了
     * @param id
     * @return
     */
    public long getSequence2(long id) {
        return (id << (TOTAL_BITS - SEQUENCE_BITS)) >>> (TOTAL_BITS - SEQUENCE_BITS);
    }

    /**
     * 通过移位解析出workerId，workerId有效位为[13,17], 左右两边都有无效位
     * 先向左移 41+5+1，移除掉41bit-时间，5bit-IDC、1bit-sign，
     * 然后右移回去41+5+1+12，从而移除掉12bit-序列号
     * @param id
     * @return
     */
    public long getWorkerId2(long id) {
        return (id << (TIME_STAMP_BITS + DATA_CENTER_ID_BITS + SIGN_BITS)) >>> (TIME_STAMP_BITS + DATA_CENTER_ID_BITS + SEQUENCE_BITS + SIGN_BITS);
    }
    /**
     * 通过移位解析出IDC_ID，dataCenterId有效位为[18,23]，左边两边都有无效位
     * 先左移41+1，移除掉41bit-时间和1bit-sign
     * 然后右移回去41+1+5+12，移除掉右边的5bit-workerId和12bit-序列号
     * @param id
     * @return
     */
    public long getDataCenterId2(long id) {
        return (id << (TIME_STAMP_BITS + SIGN_BITS)) >>> (TIME_STAMP_BITS + WORKER_ID_BITS + SEQUENCE_BITS + SIGN_BITS);
    }
    /**
     * 41bit-时间，左边1bit-sign为0，可以忽略，不用左移，所以只需要右移，并加上起始时间twepoch即可。
     * @param id
     * @return
     */
    public long getGenerateDateTime2(long id) {
        return (id >>> (DATA_CENTER_ID_BITS + WORKER_ID_BITS + SEQUENCE_BITS)) + twepoch;
    }

    public long getSequence(long id) {
        return id & ~(-1L << SEQUENCE_BITS);
    }

    public long getWorkerId(long id) {
        return id >> WORKER_ID_SHIFT & ~(-1L << WORKER_ID_BITS);
    }

    public long getDataCenterId(long id) {
        return id >> DATA_CENTER_ID_SHIFT & ~(-1L << DATA_CENTER_ID_BITS);
    }

    public long getGenerateDateTime(long id) {
        return (id >> TIMESTAMP_LEFT_SHIFT & ~(-1L << 41L)) + twepoch;
    }

    private synchronized long nextId() throws Exception {
        long timestamp = timeGen();
        // 1、出现时钟回拨问题，直接抛异常
        if (timestamp < lastTimestamp) {
            long refusedTimes = lastTimestamp - timestamp;
            // 可自定义异常类
            throw new UnsupportedOperationException(String.format("Clock moved backwards. Refusing for %d seconds", refusedTimes));
        }
        // 2、时间等于lastTimestamp，取当前的sequence + 1
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // Exceed the max sequence, we wait the next second to generate id
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 3、时间大于lastTimestamp没有发生回拨， sequence 从0开始
            this.sequence = 0L;
        }
        lastTimestamp = timestamp;

        return allocate(timestamp - this.twepoch);
    }

    //时间戳自增彻底解决时钟回拨问题
    /*private long sequence = -1L;
    private long startTimestamp = 1623947387000L;
    private synchronized  long nextId2() {
        long sequenceTmp = sequence;
        sequence = (sequence + 1) & SEQUENCE_MASK;
        // sequence =0 有可能是初始+1=0，也可能是超过了最大值等于0
        // 所以把 初始+1=0排除掉
        if (sequence == 0 && sequenceTmp >= 0) {
            // sequence自增到最大了，时间戳自增1
            startTimestamp += 1;
        }
        // 生成id
        return allocate(startTimestamp - twepoch);
    }*/

    /*
     // 记录近2S的毫秒数的sequence的缓存
    private int LENGTH = 2000;
    // sequence缓存
    private long[] sequenceCycle = new long[LENGTH];

    private synchronized long nextId() throws Exception {
        long timestamp = timeGen();
        int index = (int)(timestamp % LENGTH);
        // 1、出现时钟回拨问题，获取历史序列号自增
        if (timestamp < lastTimestamp) {
            long sequence = 0;
            do {
               if ((lastTimestamp - timestamp) > LENGTH) {
                   // 可自定义异常、告警等，短暂不能对外提供，故障转移，将请求转发到正常机器。
                   throw new UnsupportedOperationException("The timeback range is too large and exceeds 2000ms caches");
                }
                long preSequence = sequenceCycle[index];
                sequence = (preSequence + 1) & SEQUENCE_MASK;
                if (sequence == 0) {
                    // 如果取出的历史序列号+1后已经达到超过最大值，
                    // 则重新获取timestamp,重新拿其他位置的缓存
                    timestamp = tilNextMillis(lastTimestamp);
                    index = (int)(timestamp % LENGTH);
                } else {
                    // 更新缓存
                    sequenceCycle[index] = this.sequence;
                    return allocate((timestamp - this.twepoch), sequence);
                }
            } while (timestamp < lastTimestamp);
            // 如果在获取缓存的过程中timestamp恢复正常了，就走正常流程
        }
        // 2、时间等于lastTimestamp，取当前的sequence + 1
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // Exceed the max sequence, we wait the next second to generate id
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
                index = (int)(timestamp % LENGTH);
            }
        } else {
            // 3、时间大于lastTimestamp没有发生回拨， sequence 从0开始
            this.sequence = 0L;
        }
        // 缓存sequence + 更新lastTimestamp
        sequenceCycle[index] = this.sequence;
        lastTimestamp = timestamp;
        // 生成id
        return allocate(timestamp - this.twepoch);
    }*/

    /*private synchronized  long nextId3() {
        long timestamp = timeGen();
        // 1、出现时钟回拨问题，如果回拨幅度不大，等待时钟自己校正
        if (timestamp < lastTimestamp) {
            int sleepCntMax = 2;
            int sleepCnt = 0;
            do {
                long sleepTime = lastTimestamp - timestamp;
                if (sleepCnt > sleepCntMax) {
                    // 可自定义异常类
                    throw new UnsupportedOperationException(String.format("Clock moved backwards. Refusing for %d seconds", sleepTime));
                }
                if (sleepTime <= 500) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        sleepCnt++;
                        timestamp = tilNextMillis(lastTimestamp);
                    }
                } else {
                    // 可自定义异常类
                    throw new UnsupportedOperationException(String.format("Clock moved backwards. Refusing for %d seconds", sleepTime));
                }
            } while (timestamp < lastTimestamp);
        }
        // 2、时间等于lastTimestamp，取当前的sequence + 1
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // Exceed the max sequence, we wait the next second to generate id
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 3、时间大于lastTimestamp没有发生回拨， sequence 从0开始
            this.sequence = 0L;
        }
        lastTimestamp = timestamp;
        // 生成id
        return allocate(timestamp - this.twepoch);
    }*/

    private long allocate(long deltaSeconds) {
        return (deltaSeconds << TIMESTAMP_LEFT_SHIFT) | (this.dataCenterId << DATA_CENTER_ID_SHIFT) | (this.workerId << WORKER_ID_SHIFT) | this.sequence;
    }

    private long timeGen() {
        long currentTimestamp = System.currentTimeMillis();
        // 时间戳超出最大值
        if (currentTimestamp - twepoch > MAX_DELTA_TIMESTAMP) {
            throw new UnsupportedOperationException("Timestamp bits is exhausted. Refusing ID generate. Now: " + currentTimestamp);
        }
        return currentTimestamp;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws Exception {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1,2);
        long id = snowflakeIdGenerator.genID();

        System.out.println("ID=" + id + ", lastTimestamp=" + snowflakeIdGenerator.getLastTimestamp());
        System.out.println("ID二进制：" + Long.toBinaryString(id));
        System.out.println("解析ID:");
        System.out.println("Sequence=" + snowflakeIdGenerator.getSequence(id));
        System.out.println("WorkerId=" + snowflakeIdGenerator.getWorkerId(id));
        System.out.println("DataCenterId=" + snowflakeIdGenerator.getDataCenterId(id));
        System.out.println("GenerateDateTime=" + snowflakeIdGenerator.getGenerateDateTime(id));

        System.out.println("Sequence2=" + snowflakeIdGenerator.getSequence2(id));
        System.out.println("WorkerId2=" + snowflakeIdGenerator.getWorkerId2(id));
        System.out.println("DataCenterId2=" + snowflakeIdGenerator.getDataCenterId2(id));
        System.out.println("GenerateDateTime2=" + snowflakeIdGenerator.getGenerateDateTime2(id));
    }
}
