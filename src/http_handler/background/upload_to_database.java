package http_handler.background;

import org.sqlite.SQLiteException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class upload_to_database {
    Connection c = null;
    Statement stmt = null;

    public String login(String username, String password){

        String result = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/Database/userdatabase.db");
            System.out.println("Opened database successfully");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERDATABASE " +
                    "(ID TEXT PRIMARY KEY     NOT NULL ," +
                    " PASSWORD        TEXT    NOT NULL ,"+
                    " DEVICES         TEXT    NOT NULL)";
            stmt.executeUpdate(sql);

            try{
                sql = "INSERT INTO USERDATABASE (ID,PASSWORD) " +
                        "VALUES ('"+username+"','"+password+"');";
                stmt.executeUpdate(sql);
                result = "Successfully created account !!!";
            }catch(SQLiteException e){
                if(e.getMessage().contains("UNIQUE constraint failed")){
                    result = "Account already existed !!!";
                }
            }



            c.commit();

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            result = "Something Wrong With Database";
        }
        System.err.println(result);
        return result;
    }
}
