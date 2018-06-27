import socket_handler.input_data_handler;
import socket_handler.output_data_handler;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class test_case_client {

	public static void main(String[] args) throws IOException, UnknownHostException{
		int Port = 2312;
		String Host = "localhost";
		String id = "Client";
		
		Socket Client_Soc = new Socket(Host, Port);
		
		BufferedReader data_reader = new BufferedReader(new InputStreamReader(Client_Soc.getInputStream()));
		BufferedWriter data_Writer = new BufferedWriter(new OutputStreamWriter(Client_Soc.getOutputStream()));
		input_data_handler thread_in = new input_data_handler(data_reader, Host);
		output_data_handler thread_out = new output_data_handler(data_Writer);
		System.out.println("Finish Initiate I/O thread");
		while(true) {
			if(!input_data_handler.t.isAlive() || !output_data_handler.t.isAlive()) {
				System.out.println("end everything .......");
				break;
			}
		}
		
		data_reader.close();
		data_Writer.close();
		Client_Soc.close();
	}

}
