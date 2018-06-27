package socket_handler;

import java.io.BufferedReader;
import java.io.IOException;

public class input_data_handler extends Thread {
	
	public String id;
	private BufferedReader in_data; 
	private String data;
	public static Thread t;
	
	
	public input_data_handler(BufferedReader in,String i_d) {
		in_data = in;
		id = i_d;
		System.out.println("Initiate Input Handler");
		t = new Thread(new Runnable() {
			public void run() {
				System.out.println("Input Thread Intitialize");
				listen();
			}
		});
		t.start();
	}
	
	private void listen() {
		while(true) {
			try {
				data = in_data.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(data != null) {
				System.out.print("\n >> " + id + ": " + data + "\n >> ");
			}
			if(data.contentEquals("Close Socket")) {
				System.out.println("listen() end......");
				break;
			}
		}
	}

}
