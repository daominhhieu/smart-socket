

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class test_simple_function {

    public static void main( String args[] ) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/Database/user_database.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE user_info " +
                    "(EMAIL TEXT PRIMARY KEY     NOT NULL," +
                    " PASSWORD           TEXT    NOT NULL)";
            stmt.executeUpdate(sql);

            for(int i = 1; i < 10; i++){
                sql = "INSERT INTO user_info (EMAIL, PASSWORD) " +
                        "VALUES ('"+i+"@hieu.com', 'eeit2015' );";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}