package org.example;

import org.example.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        //Menu.run();
        String sql = "select current_database(), current_user, now()";
        try (Connection con = Database.open();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                System.out.println("Подключено");
                System.out.println("База " + rs.getString(1));
                System.out.println("Юзер " + rs.getString(2));
                System.out.println("Серверное время " + rs.getTimestamp(3));
            }
        } catch (Exception e) {
            System.err.println("Ошибка работы с бд" + e.getMessage());
            e.printStackTrace();
        }
    }
}