package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String sqlCommand;
    private Statement statement;
    private Connection connection;
    private PreparedStatement preparedStatement;
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            sqlCommand = "CREATE TABLE IF NOT EXISTS user " +
                    "(id BigInt PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) , lastName VARCHAR(20), age TINYINT)";
            // создание таблицы
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            connection = Util.getConnection();
            sqlCommand = "DROP TABLE IF EXISTS user";
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException | IOException throwable) {
            throwable.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        createUsersTable();
        try {
            connection = Util.getConnection();
            sqlCommand = "INSERT user(name, lastName, age) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException | IOException throwable) {
            throwable.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try {
            connection = Util.getConnection();
            sqlCommand = "DELETE FROM user where id = ?";
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            connection = Util.getConnection();
            sqlCommand = "Select * from user";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()){
                list.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (ClassNotFoundException | SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            connection = Util.getConnection();
            sqlCommand = "TRUNCATE Table user";
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
    }
}
