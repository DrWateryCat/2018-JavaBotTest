package frc.team2186.robot.net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import frc.team2186.robot.Config;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Client instance = new Client();

    public static Client getInstance() {
        return instance;
    }

    private Object lock = new Object();
    private JsonElement currentInput = null;
    private JsonObject currentOutput = new JsonObject();

    public void run() {
        while (true) {
            try {
                Socket jetson = new Socket(Config.JETSON_IP, 5800);
                PrintWriter out = new PrintWriter(jetson.getOutputStream());
                Scanner in = new Scanner(jetson.getInputStream());
                JsonParser p = new JsonParser();

                while(jetson.isConnected()) {
                    String input = in.nextLine();

                    JsonElement e = p.parse(input);
                    String output;

                    synchronized (lock) {
                        currentInput = e;
                        output = currentOutput.toString();
                    }

                    out.println(output);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JsonElement get() {
        synchronized (lock) {
            return (currentInput == null) ? new JsonObject() : currentInput;
        }
    }

    public void send(JsonObject o) {
        synchronized (lock) {
            currentInput = o;
        }
    }
}
