package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This handles all the io responsibilities for the client.
 */
public class PlayerSocket {
    private Socket socket;
    private BufferedReader sin;
    private PrintWriter sout;

    public PlayerSocket(String host, int portNumber){
        try{
            socket = new Socket(host, portNumber);
            sout = new PrintWriter(socket.getOutputStream(), true);
            sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            System.out.println(sin.readLine());
            sin.readLine();
        }catch(IOException e){
            System.err.println("Socket failed: " + e.getMessage());
        }
    }

    public void send(String message){
        sout.println(message);
    }

    public String getLine() throws IOException{
        return sin.readLine();
    }
}
