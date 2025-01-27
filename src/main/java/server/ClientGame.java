package server;

import lombok.Data;
import server.dao.PlayerDAO;
import server.games.Game;
import server.games.Player;
import server.questions.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Data
public class ClientGame extends Thread{
    private Socket socket;
    private Game game;
    private List<Question> questions;

    public ClientGame(Socket socket) {
        this.socket = socket;
        this.game = new Game();
        this.questions = new ArrayList<>();
    }

    public void selectQuestions(){
        //Seleccionar preguntas
    }

    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            //Recibir nombre de usuario
            pw.println("Introduzca su nombre de usuario");
            String name = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

            //Recibir contraseña
            pw.println("Introduzca su contraseña");
            String password = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

            //Comprobación si existe el usuario
            if(PlayerDAO.comprobarPlayer(name, password)){
                pw.println("10");
            } else {
                pw.println("11");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
