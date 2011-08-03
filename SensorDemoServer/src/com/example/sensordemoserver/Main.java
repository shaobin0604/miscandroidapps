package com.example.sensordemoserver;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static void main(String args[]) {
		try {
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				
				System.out.println("Accept client from " + clientSocket.getRemoteSocketAddress());
				
				Connection c = new Connection(clientSocket);
			}
		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		}
	}

	static class Connection extends Thread {
		BufferedReader in;
		Socket clientSocket;

		public Connection(Socket aClientSocket) {
			try {
				clientSocket = aClientSocket;
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				this.start();
			} catch (IOException e) {
				System.out.println("Connection:" + e.getMessage());
			}
		}

		public void run() {
			try {
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {	
				    System.out.println(inputLine);
				}
				
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("readline:" + e.getMessage());
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {/* close failed */
				}
			}

		}
	}
}
