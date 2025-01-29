package server;

import lombok.Data;
import server.dao.AnswerDAO;
import server.dao.GameDAO;
import server.dao.PlayerDAO;
import server.dao.QuestionDAO;
import server.games.Game;
import server.games.Player;
import server.questions.Answer;
import server.questions.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;

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

            //Comprobación si existe el usuario y añadir el jugador a la partida
            if(PlayerDAO.checkPlayer(name, password)){
                pw.println("10");
                Player player = PlayerDAO.getPlayer(name, password);
                game.setPlayer(player);

                //Comienzan las preguntas
                this.questions = QuestionDAO.getSixQuestions();
                for(Question question: questions){
                    //Guardado en la variable answers todas las respuestas de la pregunta
                    ArrayList<Answer> answers = AnswerDAO.allAnswersOfQuestion(question.getId());

                    //Envío de la categoría y la pregunta
                    pw.println("----------------------------------");
                    pw.println(" --- " + question.getCategory().getName() + " --- ");
                    pw.println("----------------------------------");
                    pw.println(question.getQuestion());

                    //Envío de las opciones
                    for(int i = 0, j = 1; i < answers.size(); i++, j++){
                        pw.println(j + ".- " + answers.get(i).getAnswer());
                    }

                    //Recibir respuesta
                    pw.println("Introduzca el número de la respuesta correcta: ");
                    int respuesta = Integer.parseInt(new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine());
                    if(answers.get(Integer.parseInt(String.valueOf(respuesta)) - 1).getCorrect() == 1){
                        question.setNumCorrect(question.getNumCorrect() + 1);
                        game.setScore(game.getScore() + 1);
                        pw.println("Bien, has acertado, tienes " + game.getScore() + " punto/s");
                    } else {
                        question.setNumFailure(question.getNumFailure() + 1);
                        pw.println("Lo siento, has fallado, la respuesta correcta era " + question.getCorrectOption() +
                                ". Tienes " + game.getScore() + " punto/s");
                    }
                    QuestionDAO.update(question);
                }
                GameDAO.create(game);

                if(player.updateMaxScore(game.getScore())){
                    PlayerDAO.updateMaxScore(game.getScore(), player);
                }
                pw.println("Has terminado. Tu puntuación es de " + game.getScore() + " punto/s. En total has jugado " +
                        GameDAO.gamesPerPlayer(player) + " partida/s y tu puntuación más alta ha sido " +
                        PlayerDAO.getMaxScore() + " punto/s.");
            } else {
                pw.println("11");
            }
            socket.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
