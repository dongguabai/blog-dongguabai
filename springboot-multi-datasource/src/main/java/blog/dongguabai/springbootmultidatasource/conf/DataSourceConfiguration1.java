package blog.dongguabai.springbootmultidatasource.conf;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 02:42
 */
@Configuration
@MapperScan(basePackages = "blog.dongguabai.springbootmultidatasource.mapper.mapper1", sqlSessionFactoryRef = "test1SqlSessionFactory")
public class DataSourceConfiguration1 {

    @Primary
    @Bean(name = "test1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource getDateSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "test1SqlSessionFactory")
    public SqlSessionFactory test1SqlSessionFactory(
            @Qualifier("test1DataSource") DataSource datasource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper1/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean("test1SqlSessionTemplate")
    public SqlSessionTemplate test1sqlsessiontemplate(
            @Qualifier("test1SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
}

