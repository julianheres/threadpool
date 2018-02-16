package co.com.jvm.concurrencia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Worker extends Thread {
    private Socket socket;
    Worker(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            outputLine = "Ready for you";
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
            	System.out.println("Processing " + inputLine);
                out.println("Task submitted");
                //Thread.sleep(2000);
                
                out.println("Respuesta");
              
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    }
}