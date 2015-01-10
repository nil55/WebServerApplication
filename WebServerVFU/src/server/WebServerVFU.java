/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author nils
 */
public class WebServerVFU {
ServerSocket s;
    protected ServerSocket getServerSocket(int port) throws Exception {
        return new ServerSocket(port);
    }
    public void runServer(int port) throws Exception {
        s = getServerSocket(port);
 
        while (true) {
            try {
                Socket serverSocket = s.accept();
                handleRequest(serverSocket);
            } catch(IOException e) {
            	 System.out.println("Failed to start server: " + e.getMessage());
                System.exit(0);
                return;
            }
        }
    }
    public void handleRequest(Socket s) {
        BufferedReader is;     // inputStream from web browser
        PrintWriter os;        // outputStream to web browser
        String request;        // Request from web browser
        PrintWriter gv;        // outputStream to web browser
 
        try {
            String webServerAddress = s.getInetAddress().toString(  );
            System.out.println("Accepted connection from " + webServerAddress);
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
 
            request = is.readLine();
            System.out.println("Server recieved request from client: " + request);
            os = new PrintWriter(s.getOutputStream(), true);
            os.println("HTTP/1.0 200");
            os.println("Content-type: text/html");
            os.println("Server-name: myserver");
            String response = "<html><head>" +
                "<title>Simpl Web Page</title></head>\n" +
                "<h1>Congratulations!!!</h1>\n" +
                "<h3>This page was returned by " + webServerAddress + "</h3>\n" +
                "<p>This is the first page hosted by your web server.\n</p>" +
                "Visit <A HREF=\"http://www.techwiki.ordak.org\"> http://www.techwiki.ordak.org</A> for more sample codes.\n" +
                "</html>\n";
            os.println("Content-length: " + response.length(  ));
            os.println("");
            os.println(response);
            os.flush();
            os.close();
            s.close();
        } catch (IOException e) {
            System.out.println("Failed to send response to client: " + e.getMessage());
        } finally {
        	if(s != null) {
        		try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return;
    }
    public static void main(String[] args) {
    WebServerVFU webServer = new WebServerVFU();
	        try {
			webServer.runServer(8080);
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}
}