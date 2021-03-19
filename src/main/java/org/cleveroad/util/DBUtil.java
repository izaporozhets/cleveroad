package org.cleveroad.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBUtil {

    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_URL = "db.url";
    public static Properties properties = null;
    private static final MysqlDataSource dataSource;

    static {
        try{
            properties = new Properties();
            properties.load(new FileInputStream("src/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataSource = new MysqlDataSource();
        dataSource.setUrl(properties.getProperty(DB_URL));
        dataSource.setUser(properties.getProperty(DB_USERNAME));
        dataSource.setPassword(properties.getProperty(DB_PASSWORD));
        dataSource.setDatabaseName("users");
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

}
