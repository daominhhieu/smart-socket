package http_handler.background;

import org.sqlite.SQLiteException;

import java.sql.*;

public class upload_to_database {
    private Connection c = null;
    private Statement stmt = null;
    private String result = null;
    public static String socket_db_file_name = "socket_database";
    public static String user_db_file_name = "user_database";




    public String upload_data(String database_file, String data, String option){

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/Database/"+database_file+".db");
            System.out.println("Opened database successfully");
            c.setAutoCommit(false);

            stmt = c.createStatement();

            switch(option){
                case "signup"                           :signup(data);
                                                        break;
                case "login"                            :login(data);
                                                        break;
                case "register_device"                  :register_device(data);
                                                        break;
                case "socket_state_update"              :socket_state_update(data);
                                                        break;
                case "find_device_from_user_email"      :find_device_from_user_email(data);
                                                        break;
                case "check_key_from_socket_database"   :check_key_from_socket_database(data);
                                                        break;
            }

            c.commit();

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            result = "Something Wrong With Database";
        }
        return result;
    }
    public void login(String data) throws SQLException {
        String email        = split(data)[0];
        String passsword = split(data)[1];

        result = "Access denied";

        ResultSet rs = stmt.executeQuery( "SELECT * FROM user_info;" );
        while (rs.next()){
            if(rs.getString("EMAIL").equals(email)){
                if(rs.getString("PASSWORD").equals(passsword)){
                    result = "Access granted";
                    break;
                }
            }
        }
    }

    public void signup(String data){
        String email        = split(data)[0];
        String password    = split(data)[1];

        try{
            String sql = "INSERT INTO user_info (EMAIL, PASSWORD) " +
                    "VALUES ("+email+","+password+");";
            stmt.executeUpdate(sql);

            sql = "CREATE TABLE "+email+" " +
                    "(D_KEY TEXT PRIMARY KEY     NOT NULL," +
                    " D_NAME           TEXT    NOT NULL)";
            stmt.executeUpdate(sql);

            result = "Successfully created account !!!";
        }catch(SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                result = "Account already existed !!!";
            }
        }
    }

    private void check_key_from_socket_database(String data) throws SQLException {
        String email       = split(data)[0];
        String device_key  = split(data)[1];

        ResultSet rs = stmt.executeQuery( "SELECT * FROM soc_info;" );
        result = "Valid";
        while (rs.next()){
            if(rs.getString("D_KEY").equals(device_key)){
                if(rs.getString("EMAIL").equals(email)){
                    String  sql = "UPDATE sock_info set EMAIL = '"+email+"' where D_KEY='"+device_key+"';";
                    stmt.executeUpdate(sql);
                    result = "Not valid";
                    break;
                }
            }
        }
    }

    private void register_device(String data){
        String email       = split(data)[0];
        String device_key  = split(data)[1];
        String device_name = split(data)[2];

        try {
            if(upload_data(socket_db_file_name,data,"check_key_from_socket_database").equals("Valid")) {

                String sql = "INSERT INTO " + email + " (D_NAME,D_KEY) " +
                        "VALUES (" + device_name + "," + device_key + ");";
                stmt.executeUpdate(sql);
                result = "Device Added";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "Device not available";
        }
    }

    public void find_device_from_user_email(String data)throws SQLException{
        String email = split(data)[0];

        ResultSet rs_1 = stmt.executeQuery( "SELECT * FROM "+email+";" );

        result =  rs_1.getString("D_KEY");
    }

    public void socket_state_update(String data){

        String soc    = split(data)[1];
        String state  = split(data)[2];

        try{
            String sql = "UPDATE "+upload_data(socket_db_file_name,data,"find_device_from_user_email" )+" set STATE = "+state+" where SOC = "+soc+";";
            stmt.executeUpdate(sql);
            result = "Success";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] split(String data){
        int split_1, split_2 = 0, element=0;
        String[] result = null;

        for(int i=0; i < data.length(); i++){
            if(data.indexOf(i) == '&'){
                split_1 = split_2;
                split_2 = i;
                char[] c = new char[split_2-split_1-1];
                data.getChars(data.indexOf(split_1)+1,data.indexOf(split_2),c,0);
                result[element] = String.copyValueOf(c);
                element ++ ;
            }
        }
        return result;
    }
}
