package com.example.demo.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
/*import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;*/
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/19 13:29
 */
/*@MapperScan("com.example.demo.mapper")
@EnableTransactionManagement
@Configuration*/
public class MyBatisPlusConfig {

    //3.0.5版本
    /*@Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        return new OptimisticLockerInterceptor();
    }

    // 分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    // 逻辑删除组件！
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }*/


    /**
     * 创建sqlSessionFactory
     *
     * @return
     * @throws Exception
     */
    @Bean(name = "mySqlSessionFactory")
    public SqlSessionFactory mySqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*/*.xml"));

        //mybatis配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactory.setConfiguration(configuration);
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        //0为数据库ID自增
        dbConfig.setIdType(IdType.AUTO);
        dbConfig.setLogicDeleteField("isDelete");
        dbConfig.setLogicDeleteValue("1");
        dbConfig.setLogicNotDeleteValue("0");
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setMetaObjectHandler(new ObjectFillHandler());
        sqlSessionFactory.setGlobalConfig(globalConfig);
        sqlSessionFactory.setPlugins(mybatisPlusInterceptor());
        return sqlSessionFactory.getObject();
    }

    /**
     * mybatisplus插件
     */
//    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //自动分页
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁
        //支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
        //整数类型下 newVersion = oldVersion + 1
        //newVersion 会回写到 entity 中
        //仅支持 updateById(id) 与 update(entity, wrapper) 方法
        //在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //防全表更新与删除
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    /**
     * 创建sqlSession模板
     *
     * @return
     */
    @Bean(name = "mySqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(SqlSessionFactory mySqlSessionFactory) {
        return new SqlSessionTemplate(mySqlSessionFactory);
    }

    /**
     * 创建sqlSession模板
     *
     * @return
     */
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
