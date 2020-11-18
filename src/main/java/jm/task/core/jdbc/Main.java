package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Игорь", "Иванов", (byte) 1);
        userService.saveUser("Елена", "Иванова", (byte) 2);
        userService.saveUser("Петр", "Иванов", (byte) 3);
        userService.saveUser("Свекл", "Иванов", (byte) 4);
        (userService.getAllUsers()).forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}