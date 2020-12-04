/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Controller.ModelController;
import Server.Controller.ServerController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private ExecutorService executorPool;
    private final int PORT = 8081;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            executorPool = Executors.newCachedThreadPool();
            ModelController.readDataFromTextFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            while (true) {
                System.out.println("Server is running...");
                clientSocket = serverSocket.accept();
                System.out.println("Client has connected...starting new controller");

                ServerController serverController = new ServerController(clientSocket);
//                ModelController modelController = new ModelController(new ServerController(clientSocket), new RetailShopRunner());
                executorPool.execute(serverController);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorPool.shutdown();
        stop();
    }
    
    public void stop(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
