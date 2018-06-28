import org.sqlite.SQLiteException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class testSocket {
    public static void main(String[] args) throws IOException {
        Connection c = null;
        Statement stmt = null;
        int a = 0;

        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/Database/socket_database.db");
            System.out.println("Opened database successfully");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO button_1 (STATE) " +
                    "VALUES (2);";
            stmt.executeUpdate(sql);

            c.commit();

            stmt.close();
            c.close();


        }catch(java.sql.SQLException e){
            System.err.println("loi roi dit me may");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
