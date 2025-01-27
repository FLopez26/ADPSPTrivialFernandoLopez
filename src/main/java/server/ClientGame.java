package server;

import lombok.Data;
import server.dao.AnswerDAO;
import server.dao.PlayerDAO;
import server.dao.QuestionDAO;
import server.games.Game;
import server.questions.Answer;
import server.questions.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

@Data
public class ClientGame extends Thread{
    private Socket socket;
    private Game game;
    private ArrayList<Question> questions;

    public ClientGame(Socket socket) {
        this.socket = socket;
        this.game = new Game();
        this.questions = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            //Recibir nombre de usuario
            pw.println("Introduzca su nombre de usuario:");
            String name = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

            //Recibir contraseña
            pw.println("Introduzca su contraseña:");
            String password = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

            //Comprobación si existe el usuario
            if(PlayerDAO.comprobarPlayer(name, password)){
                pw.println("10");
            } else {
                pw.println("11");
            }

            //Comienzan las preguntas
            this.questions = QuestionDAO.getSixQuestions();
            for(Question question: questions){
                ArrayList<Answer> answers = AnswerDAO.allAnswersOfQuestion(question.getId());
                pw.println(question.getQuestion());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
