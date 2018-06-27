import socket_handler.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class main_socket_server {

	public static void main(String[] args) throws IOException {
		
		int Port = 8080;
		
		ServerSocket Server_Soc = new ServerSocket(Port);
		Socket Soc = Server_Soc.accept();
		String Client_id = Soc.getRemoteSocketAddress().toString();
		
		BufferedReader data_reader = new BufferedReader(new InputStreamReader(Soc.getInputStream()));
		BufferedWriter data_Writer = new BufferedWriter(new OutputStreamWriter(Soc.getOutputStream()));
		
		
		input_data_handler thread_in = new input_data_handler(data_reader, Client_id);
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
		
		Soc.close();
		
		Server_Soc.close();
		
		
		
	}

}
