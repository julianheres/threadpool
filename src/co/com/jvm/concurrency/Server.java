package co.com.jvm.concurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public class Server {
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java KKMultiServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;

		MyThreadPoolExecutor myTPE = new MyThreadPoolExecutor(10, // Size according to Amdahl's law
				10, // Size according to Amdahl's law
				30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10, true), // Size according to Amdahl's law
				new AbortPolicy());

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				myTPE.execute(new Runnable() {
					@Override
					public void run() {
						try (Socket socket = serverSocket.accept();
								PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
								BufferedReader in = new BufferedReader(
										new InputStreamReader(socket.getInputStream()));) {
							String inputLine, outputLine;
				            outputLine = "Ready for you";
				            out.println(outputLine);

				            while ((inputLine = in.readLine()) != null) {
				            	System.out.println("Processing " + inputLine);
				                out.println("Task submitted");
				                Thread.sleep(20000);
				                out.println("Respuesta");
				              
				                if (outputLine.equals("Bye"))
				                    break;
				            }
				            socket.close();
						} catch (IOException e) {
							System.err.println("Could not accept incoming task: " + e.getMessage());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		} 
	}
}