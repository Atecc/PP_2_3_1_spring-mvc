package web.config;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class AppConfig {


   private final Environment env;
   @Autowired
   public AppConfig(Environment env) {
      this.env = env;
   }

   @Bean
   public DataSource getDataSource() {
      BasicDataSource ds = new BasicDataSource();
      ds.setUrl(env.getRequiredProperty("db.url"));
      ds.setDriverClassName(env.getRequiredProperty("db.driver"));
      ds.setUsername(env.getRequiredProperty("db.username"));
      ds.setPassword(env.getRequiredProperty("db.password"));
      return ds;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
      LocalContainerEntityManagerFactoryBean em =new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(getDataSource());
      em.setPackagesToScan("web.model");
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      em.setJpaProperties(getHibernateProperties());

       return em;
   }

   public Properties getHibernateProperties() {
      try {
         Properties properties = new Properties();
         InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
         properties.load(is);
         return properties;
      } catch (IOException e) {
         throw new IllegalArgumentException("Не найден проперти файл",e);
      }
   }
   @Bean
   public JpaTransactionManager getTransactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
      return transactionManager;
   }
}
