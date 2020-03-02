package cinema.configuration;

import cinema.model.CinemaHall;
import cinema.model.Movie;
import cinema.model.MovieSession;
import cinema.model.Order;
import cinema.model.Role;
import cinema.model.ShoppingCart;
import cinema.model.Ticket;
import cinema.model.User;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = {
        "cinema.dao.hibernateimpl",
        "cinema.service.impl",
        "cinema.security"
})
public class AppConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean getLocalSessionFactory() {
        LocalSessionFactoryBean sessionFactory
                = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("hibernate.hbm2ddl.auto"));
        sessionFactory.setHibernateProperties(properties);
        sessionFactory.setAnnotatedClasses(Movie.class,User.class,
                CinemaHall.class, MovieSession.class, Ticket.class,
                Order.class, ShoppingCart.class, Role.class);
        return sessionFactory;
    }

}
