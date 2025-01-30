package server;

import jdk.swing.interop.SwingInterOpUtils;
import server.dao.GameDAO;
import server.dao.HibernateUtil;
import server.dao.PlayerDAO;
import server.dao.QuestionDAO;
import server.games.Player;

import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        Scanner sc = new Scanner(System.in);
        int opc = -1;

        while(opc !=0){
            System.out.println("-----------------------------------------");
            System.out.println("Bienvenido al Trivial:");
            System.out.println(" 1.- Arrancar el servidor.");
            System.out.println(" 2.- Histórico de jugadas.");
            System.out.println(" 3.- Top 10 de jugadas.");
            System.out.println(" 4.- Resetear los resultados.");
            System.out.println(" 5.- Preguntas mas fáciles y dificiles.");
            System.out.println(" 6.- Creación de jugadores.");
            System.out.println(" 0.- Salir");
            System.out.println("------------------------------------------");
            try{
                opc = sc.nextInt();
                switch (opc){
                    case 1:
                        iniciarServidor();
                        break;
                    case 2:
                        historicoJugadas();
                        break;
                    case 3:
                        top10();
                        break;
                    case 4:
                        reset();
                        break;
                    case 5:
                        preguntasFacilesDificiles();
                        break;
                    case 6:
                        crearJugador();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Introduzca una opción válida.");
                }
            } catch (Exception e){
                System.out.println("Introduzca un número.");
                String error = sc.nextLine();
            }
        }
    }

    /* MÉTODOS DE LAS OPCIONES DEL MENÚ */

    //CASO DEL MENÚ 1
    /**
     * Se inicia el servidor lanzando un hilo de la clase ServerClient.
     */
    public static void iniciarServidor(){
        ServerClient serverClient = new ServerClient();
        serverClient.start();
    }

    //CASO DEL MENÚ 2
    /**
     * Muestra todas las partidas jugadas.
     */
    public static void historicoJugadas(){
        System.out.println("Mostrando todas las partidas jugadas:");
        GameDAO.verTodo().forEach(System.out::println);
    }

    //CASO DEL MENÚ 3
    /**
     * Muestra el top 10 de las partidas jugadas.
     */
    public static void top10(){
        System.out.println("Mostrando el top 10 de partidas:");
        GameDAO.top10().forEach(System.out::println);
    }

    //CASO DEL MENÚ 4
    /**
     * Resetea los resultados de las partidas jugadas.
     */
    public static void reset(){
        GameDAO.eliminarTodo();
        PlayerDAO.resetMaxScores();
        System.out.println("Partidas y puntuación máxima de los jugadores eliminadas.");
    }

    //CASO DEL MENÚ 5
    /**
     * Muestra las 5 preguntas más fáciles y las 5 más difíciles.
     */
    public static void preguntasFacilesDificiles(){
        System.out.println("Mostrando las 5 preguntas más fáciles:");
        QuestionDAO.getFaciles().forEach(System.out::println);
        System.out.println("-----------------------------------------");
        System.out.println("Mostrando las 5 preguntas más difíciles:");
        QuestionDAO.getDificiles().forEach(System.out::println);
    }

    //CASO DEL MENÚ 6
    /**
     * Crea un usuario introduciendo un nombre y contraseña por pantalla, siempre que este no exista ya.
     */
    public static void crearJugador(){
        System.out.println("Introduce el nombre del jugador:");
        Scanner sc = new Scanner(System.in);
        String nombre = sc.nextLine();
        System.out.println("Introduce la contraseña:");
        String password = sc.nextLine();
        Player player = new Player(nombre, password);
        if(PlayerDAO.checkPlayer(nombre, password)){
            System.err.println("El jugador ya existe.");
        } else {
            PlayerDAO.create(player);
            System.out.println("Jugador creado.");
        }
    }
}