package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
	try (ServerSocket serverSocket = new ServerSocket(8090)) {
        System.out.println("Server STarted");

        while (true) {
            // ожидаем подключения
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // для подключившегося клиента открываем потоки чтения и записи
            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter output = new PrintWriter(socket.getOutputStream())) {
                // ждём первой строки ввода
                while (!input.ready());

                // считываем и печатаем всё, что было отправлено клиентом
                System.out.println();
                while (input.ready()) {
                    System.out.println(input.readLine());
                }

                // отправляем ответ
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<p>Привет всем!</p>");
                output.flush();

                // по окончанию выполнения блока try-with-resources потоки,
                // а вместе с ними и соединения будут закрыты
                System.out.println("Client disconnected!");
            }
        }
    }  catch (IOException ex) {
        ex.printStackTrace();
        }

    }
}
