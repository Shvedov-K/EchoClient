package org.example;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    private static Conf conf = Conf.GetInstance();
    static boolean debug = Boolean.parseBoolean(conf.GetConfs().get("debug"));
    private static final Logger log = Logger.getLogger(EchoClient.class);

    public static void main(String[] args) throws IOException {
        String serverHostname = conf.GetConfs().get("server_adress");
        int serverPort = Integer.parseInt(conf.GetConfs().get("server_port"));
        boolean debug = Boolean.parseBoolean(conf.GetConfs().get("debug"));

        if (args.length > 0) {
            serverHostname = args[0];
        }
        if(debug) log.debug ("Attempting to connect to host " + serverHostname + " on port " + serverPort);

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, serverPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            if(debug) log.error ("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            if(debug) log.error ("Couldn't get I/O for " + "the connection to: " + serverHostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println ("Type Message (\":q\" to quit)");
        System.out.println("Type your name first");
        out.println(stdIn.readLine());
        System.out.println("Server recorded you like: " + in.readLine());
        while ((userInput = stdIn.readLine()) != null)
        {
            out.println(userInput);
            if (userInput.equals(":q")) {
                break;
            }
            System.out.println("echo: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}