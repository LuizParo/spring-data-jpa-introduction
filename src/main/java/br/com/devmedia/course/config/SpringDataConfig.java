package br.com.devmedia.course.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.devmedia.course.repository.PersonRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {PersonRepository.class})
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class SpringDataConfig {
    
    @Value(value = "${jdbc.user}")
    private String username;
    
    @Value(value = "${jdbc.pass}")
    private String password;
    
    @Value(value = "${jdbc.driver}")
    private String driver;
    
    @Value(value = "${jdbc.url}")
    private String url;
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(emf);
        manager.setJpaDialect(new HibernateJpaDialect());
        
        return manager;
    }
    
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        
        return adapter;
    }
    
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(this.jpaVendorAdapter());
        factory.setPackagesToScan("br.com.devmedia.course.entity");
        factory.setDataSource(this.dataSource());
        factory.afterPropertiesSet();
        
        return factory.getObject();
    }
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        dataSource.setDriverClassName(this.driver);
        dataSource.setUrl(this.url);
        
        return dataSource;
    }
}