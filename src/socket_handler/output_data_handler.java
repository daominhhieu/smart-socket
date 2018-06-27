package socket_handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class output_data_handler extends Thread {
	
	private BufferedWriter out_data;
	private String data;
	private Scanner i = new Scanner(System.in);
	public static Thread t;
	
	
	public output_data_handler(BufferedWriter out) {
		out_data = out;
		System.out.println("Initiate Output Handler");
		t = new Thread(new Runnable() {
			public void run() {
				System.out.println("Output Thread Intitialize");
				send();
			}
		});
		t.start();
	}
	
	private void send() {
		while(true) {
			System.out.print(" >> ");
			data = i.nextLine();
			try {
				out_data.write(data);
				out_data.newLine();
				out_data.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(data.contentEquals("Close Socket")) {
				System.out.println("send()  end....");
				break;
			}
		}
	}

}
