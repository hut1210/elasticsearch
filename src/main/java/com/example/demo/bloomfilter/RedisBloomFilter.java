package com.example.demo.bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 15:39
 */
@ConfigurationProperties("bloom.filter")   // 通过application.yml自动装配属性值
@Component
@Data
public class RedisBloomFilter {
    /**
     * 本Bloom过滤器的名字
     */
    private String bloomFilterName;

    /**
     * 预估要插入数据量(必须为正数)
     */
    private long expectedInsertions;

    /**
     * 预期可接受的误判率，必须大于0（不能等于0）
     */
    private double fpp;

    /**
     * Redis中bit位的总数量（bit数组长度）
     */
    private long bitSize = 0;

    /**
     * 每个元素进行执行哈希的函数个数
     */
    private int numHashFunctions;

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        this.bitSize = optimalNumOfBits(expectedInsertions, fpp);
        this.numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, this.bitSize);
    }

    /**
     * 将元素放入Bloom过滤器
     */
    public void put(String element) {
        // 先计算Hash（多个函数）获得多个坐标
        long[] bitIndexes = getBitIndexes(element);

        // 然后将结果放入到Redis中（使用pipeline方式，多次bit操作合并一次完成，提升效率）
        redisTemplate.executePipelined(new RedisCallback<Object>() {
                                           @Override
                                           @Nullable
                                           public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                                               for (long one_bitIndex : bitIndexes) {
                                                   redisConnection.setBit(bloomFilterName.getBytes(), one_bitIndex, true);
                                               }
                                               redisConnection.close();
                                               return null;
                                           }
                                       }
        );
    }

    /**
     * 判断元素是否在Bloom过滤器中已存在
     *
     * @return true=存在，  false=不存在。
     */
    public boolean mightContain(String element) {
        // 先计算Hash（多个函数）获得多个坐标
        long[] bitIndexes = getBitIndexes(element);

        // 然后将结果放入到Redis中（使用pipeline方式，多次bit操作合并一次完成，提升效率）
        List list = redisTemplate.executePipelined(new RedisCallback<Object>() {
                                                       @Override
                                                       @Nullable
                                                       public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                                                           for (long one_bitIndex : bitIndexes) {
                                                               redisConnection.getBit(bloomFilterName.getBytes(), one_bitIndex);
                                                           }
                                                           redisConnection.close();
                                                           return null;
                                                       }
                                                   }

        );

        return !list.contains(false); // 如有包含（一个或多个）fasle，则一定不存在。 否则结果视为存在
    }

    /**
     * 根据元素计算Hash（多个Hash函数），每个Hash算出bit数组中的坐标（多个）
     * 本函数代码实现 参考自com.google.common.hash.BloomFilterStrategies代码中put方法，有改动
     * This strategy uses all 128 bits of {@link Hashing#murmur3_128} when hashing. It looks different
     * than the implementation in MURMUR128_MITZ_32 because we're avoiding the multiplication in the
     * loop and doing a (much simpler) += hash2. We're also changing the index to a positive number by
     * AND'ing with Long.MAX_VALUE instead of flipping the bits.
     *
     * @param element 元素内容
     * @return 返回存放坐标的数组
     */
    private long[] getBitIndexes(String element) {
        // 第1步初步得到Hash
        byte[] bytes = Hashing.murmur3_128().hashString(element, Charsets.UTF_8).asBytes();  // 得到128bit（16字节）的结果
        long hash1 = Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]); // lowerEight 8字节
        long hash2 = Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]); // upperEight 8字节
        String aa = this.bloomFilterName;

        // 第2步 根据Hash函数的个数，分别计算。对同一个元素得到不同的hash结果（bit位图中的坐标）
        long[] bitIndexes = new long[this.numHashFunctions]; // 存放坐标的数组
        long combinedHash = hash1;
        for (int i = 0; i < numHashFunctions; i++) {
            // Make the combined hash positive and indexable  通过Long.MAX_VALUE(二进制为0111…1111)，直接将开头的符号位去掉，从而转变为正数。
            bitIndexes[i] = (combinedHash & Long.MAX_VALUE) % bitSize;
            combinedHash += hash2;
        }
        return bitIndexes;

    }


    /**
     * 本函数代码实现 拷贝自com.google.common.hash.BloomFilter代码中optimalNumOfHashFunctions方法
     * Computes the optimal k (number of hashes per element inserted in Bloom filter), given the
     * expected insertions and total number of bits in the Bloom filter.
     *
     * <p>See http://en.wikipedia.org/wiki/File:Bloom_filter_fp_probability.svg for the formula.
     *
     * @param expectedInsertions 预估要插入数据量 expected insertions(must be positive)
     * @param numBits            bitmap长度  total number of bits in Bloom filter (must be positive)
     * @return 根据入参，估算出需要的hash函数个数。
     */
    private int optimalNumOfHashFunctions(long expectedInsertions, long numBits) {
        // (numBits / expectedInsertions) * log(2), but avoid truncation due to division!
        return Math.max(1, (int) Math.round((double) numBits / expectedInsertions * Math.log(2)));
    }

    /**
     * 本函数代码实现 拷贝自com.google.common.hash.BloomFilter代码中optimalNumOfBits方法
     * Computes m (total bits of Bloom filter) which is expected to achieve, for the specified
     * expected insertions, the required false positive probability.
     *
     * <p>See http://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives for the
     * formula.
     *
     * @param expectedInsertions 预估要插入数据量 expected insertions(must be positive)
     * @param fpp                预期可接受的误判率，false positive rate (must be 0 < p < 1)
     * @return 根据入参，估算出需要的bitmap长度
     */
    private long optimalNumOfBits(long expectedInsertions, double fpp) {
        if (fpp == 0) {
            fpp = Double.MIN_VALUE;
        }
        return (long) (-expectedInsertions * Math.log(fpp) / (Math.log(2) * Math.log(2)));
    }
}
