package http_handler.background;

public class data_processing {


    public String[] split(String in, String split_first_point, String split_second_point){

        String Password =null;
        String Username =null;

        if(in.indexOf(split_first_point)!=-1 && in.indexOf(split_second_point)!= -1){
            int len_of_username = in.indexOf(split_second_point) - in.indexOf(split_first_point) - split_first_point.length();
            char[] testdata1 = new char[len_of_username];

            in.getChars(in.indexOf(split_first_point)+split_first_point.length(),in.indexOf(split_second_point),testdata1,0);
            Password = String.copyValueOf(testdata1);


            int len_testdata2 = in.length() - in.indexOf(split_second_point) - split_second_point.length();
            char[] testdata2 = new char[len_testdata2];

            in.getChars(in.indexOf(split_second_point)+split_second_point.length(),in.length(),testdata2,0);
            Username = String.copyValueOf(testdata2);

        }else{
            System.out.println("Message Invalid");
        }
        return new String[]{Password, Username};
    }




}
