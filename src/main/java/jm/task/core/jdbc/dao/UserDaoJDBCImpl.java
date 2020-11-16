package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

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
        sqlCommand = "CREATE TABLE IF NOT EXISTS User (id BigInt PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) , lastName VARCHAR(20), age TINYINT)";
        try {
            connection = Util.getOracleConnection();
            statement = connection.createStatement();
            // создание таблицы
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        sqlCommand = "DROP TABLE IF EXISTS User";
        try {
            connection = Util.getOracleConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        createUsersTable();
        sqlCommand = "INSERT User(name, lastName, age) VALUES (?, ?, ?)";
        try {
            connection = Util.getOracleConnection();
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        sqlCommand = "DELETE FROM User where id = ?";
        try {
            connection = Util.getOracleConnection();
            preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        sqlCommand = "Select * from User";
        try {
            connection = Util.getOracleConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()){
//    String empName = rs.getString("name");
//    String emplastName = rs.getString("lastName");
//    Byte empAge = rs.getByte("age");
                list.add(new User(rs.getString("name"), rs.getString("lastName"),
                        rs.getByte("age")));
            }

        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        sqlCommand = "TRUNCATE Table User";
        try {
            connection = Util.getOracleConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
