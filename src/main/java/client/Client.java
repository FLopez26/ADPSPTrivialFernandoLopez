package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int puerto = 48120;
        String ip = "localhost";
        try {
            Socket socket = new Socket(ip, puerto);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensaje = in.readLine();
            System.out.println(mensaje);

            Scanner sc = new Scanner(System.in);
            String respuesta = sc.nextLine();
            out.println(respuesta);

            System.out.println(in.readLine());
            respuesta = sc.nextLine();
            out.println(respuesta);

            String code = in.readLine();
            if (code.equals("10")) {
                System.out.println("Sesión iniciada");
                for (int f = 0; f < 4; f++) {
                    for (int i = 0; i < 5; i++) {
                        //Nombre de la categoria
                        System.out.println(in.readLine());
                        System.out.println(in.readLine());
                        System.out.println(in.readLine());
                        //Pregunta
                        System.out.println(in.readLine());
                        //Opciones de la pregunta
                        System.out.println(in.readLine());
                        System.out.println(in.readLine());
                        System.out.println(in.readLine());
                        System.out.println(in.readLine());
                        //Usuario responde
                        respuesta = sc.nextLine();
                        out.println(respuesta);
                        //Respuesta correcta o incorrecta
                        System.out.println(in.readLine());
                    }
                }
                System.out.println(in.readLine());
            } else if (code.equals("11")){
                System.out.println("Usuario o contraseña incorrectos.");
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
