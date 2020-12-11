package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static Properties properties;
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    private static String dialect;

    private static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try(InputStream in = Files.newInputStream(Paths.get(".\\src\\main\\resources\\database.properties"))){
                properties.load(in);
                url = properties.getProperty("url");
                username = properties.getProperty("username");
                password = properties.getProperty("password");
                driver = properties.getProperty("driver");
                dialect = properties.getProperty("dialect");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // реализуйте настройку соеденения с БД
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        loadProperties();
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                loadProperties();
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, driver);
                settings.put(Environment.URL, url);
                settings.put(Environment.USER, username);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, dialect);
//                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (JDBCConnectionException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return sessionFactory;
    }
}
