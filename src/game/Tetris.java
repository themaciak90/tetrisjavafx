package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main class of a game
 */
public class Tetris extends Application {
    // zmienne
    public static final int MOVEMENT = 25;

    public static final int SIZE = 25;

    public static int XMAX = SIZE * 12;

    public static int YMAX = SIZE * 24;

    public static int[][] GRID = new int[XMAX / SIZE][YMAX / SIZE];

    private static Pane gamepane = new Pane();

    private static Block currentblock;

    private static Scene gamescene = new Scene(gamepane, XMAX + 150, YMAX);

    private static Menu menu = new Menu();

    public static int score = 0;

    public static int peak = 0;

    public static boolean isRunning = false;


    private static Block nextblock = Controller.createBlock();

    public static int linesfilled = 0;

    private static int falltime = 3;

    private static Canvas canvas = new Canvas(XMAX + 150, YMAX);

    private static Rectangle prevblock1, prevblock2, prevblock3, prevblock4;

    private static int treshold = 500;

    private static KeyController keyController = new KeyController();

    public static int gamelevel = 1;

    private static Tile[][] tiles = new Tile[XMAX/ SIZE][YMAX/ SIZE];



    //tworzenie sceny i zaczynanie gry

    /**
     * Main method that launches the game
     * @param args Array of arguments
     */

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method creating game scene and creating the first block
     */

    public void creategamescene(){
       Line line = new Line(XMAX, 0, XMAX, YMAX);

       final Text next = new Text("Nastepny: ");
       next.setStyle("-fx-font: 20 arials");
       next.setY(125);
       next.setX(XMAX + 5);
       Button exitbutton = new Button("Wyjscie");
       exitbutton.setLayoutX(XMAX + 5);
       exitbutton.setLayoutY(400);
       exitbutton.setMinSize(50, 50);;
       exitbutton.setOnAction(e -> System.exit(0));

       for(int y = 0; y < YMAX/ SIZE; y++){
           for(int x = 0; x < XMAX/ SIZE; x++){
               Tile tile = new Tile(x,y);

               tiles[x][y] = tile;
           }
       }
       for(int y = 0; y < YMAX/ SIZE; y++){
           for(int x = 0; x < XMAX/ SIZE; x++){


               gamepane.getChildren().add(tiles[x][y]);
           }
       }


       gamepane.getChildren().addAll(line, next,canvas);

       Block a = nextblock;
       gamepane.getChildren().addAll(a.a, a.b, a.c, a.d);
       keyController.addObserver(a);
       MoveOnKeyPress();
       currentblock = a;
       nextblock = Controller.createBlock();
       Block newblock = nextblock;
       Rectangle one = new Rectangle(SIZE - 1, SIZE - 1);
       Rectangle two = new Rectangle(SIZE - 1, SIZE - 1);
       Rectangle three = new Rectangle(SIZE - 1, SIZE - 1);
       Rectangle four = new Rectangle(SIZE - 1, SIZE - 1);
       shownextblock(one, two, three, four, newblock.getName(), gamepane);
       prevblock1 = one;
       prevblock2 = two;
       prevblock3 = three;
       prevblock4 = four;
       exitbutton.getStyleClass().clear();
       exitbutton.getStylesheets().add("Button.css");
       exitbutton.getStyleClass().add("button");
       gamepane.getChildren().add(exitbutton);
   }


    /**
     * method that runs the game and switches scenes
     * @param stage window of a game
     */

    @Override
    public void start(Stage stage) {
        for (int[] a : GRID) {
            Arrays.fill(a, 0);
        }
        //Menu
        menu.createmenu(stage, gamepane, gamescene);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        creategamescene();


        stage.setTitle("TETRIS");
        stage.show();


        final Text scoretext = new Text("Wynik: ");
        scoretext.setStyle("-fx-font: 20 arials");
        scoretext.setY(50);
        scoretext.setX(XMAX + 5);
        scoretext.setFill(Color.HOTPINK);
        final Text linestext = new Text("Linie: ");
        linestext.setStyle("-fx-font: 20 arials");
        linestext.setY(75);
        linestext.setX(XMAX + 5);
        linestext.setFill(Color.HOTPINK);
        final Text level = new Text("Poziom: ");
        level.setStyle("-fx-font: 20 arials");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.HOTPINK);

        gamepane.getChildren().addAll(scoretext, linestext, level);
        new AnimationTimer(){
            long lasttick = 0;
            @Override
            public void handle(long now) {
                if(lasttick == 0){
                    lasttick = now;
                    tick(gc);
                    return;
                }
                if(now - lasttick > 1000000000 / falltime){
                    lasttick = now;
                    tick(gc);
                }
                if(isRunning){
                    scoretext.setText("Wynik = " + Integer.toString(score));
                    linestext.setText("Linie = " + Integer.toString(linesfilled));
                    level.setText("Poziom = " + Integer.toString(gamelevel));
                }


            }

        }.start();
        gamepane.setStyle("-fx-background-color: #003366");

    }


    private void tick(GraphicsContext gc){

        if (currentblock.a.getY() == 0 || currentblock.b.getY() == 0 || currentblock.c.getY() == 0 || currentblock.d.getY() == 0)
            peak++;
        else
            peak = 0;

        if (peak == 2 && isRunning == true) {
            //koniec gry

            Text gameover = new Text("KONIEC GRY");
            gameover.setFill(Color.RED);
            gameover.setStyle("-fx-font: 70 arial");
            gameover.setY(250);
            gameover.setX(10);
            gamepane.getChildren().add(gameover);
            isRunning = false;
        }

        if (isRunning) {
            if(score > treshold) {
                falltime++;
                treshold +=500;
                gamelevel++;
            }
            blockFall(currentblock);
        }

    }



    private static void MoveOnKeyPress() {
        gamescene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyController.notifyObservers(keyEvent);
            }
        });
    }








    private static void deleteRow(Pane pane) {
        ArrayList<Integer> linestodelete = new ArrayList<Integer>();
        ArrayList<Node> bloki = new ArrayList<Node>();
        ArrayList<Node> nowe_bloki = new ArrayList<Node>();
        int fullrows = 0;
        //sprawdzenie czy linia jest zapelnienoa
        for (int i = 0; i < GRID[0].length; i++) {
            for (int j = 0; j < GRID.length; j++) {
                if (GRID[j][i] == 1)
                    fullrows++;
            }
            if (fullrows == GRID.length)
                linestodelete.add(i);
            fullrows = 0;
        }




        if (linestodelete.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle) {
                        if (node != prevblock1 && node != prevblock2 && node != prevblock3 && node != prevblock4)
                            bloki.add(node);
                    }
                }
                Tetris.score += 50;
                Tetris.linesfilled++;


                for (Node node : bloki) {
                    Rectangle a = (Rectangle) node;
                    try {
                        if (a.getY() == linestodelete.get(0) * Tetris.SIZE) {
                            Tetris.GRID[(int) a.getX() / Tetris.SIZE][(int) a.getY() / Tetris.SIZE] = 0;
                            pane.getChildren().remove(node);
                        } else
                            nowe_bloki.add(node);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }

                try {
                    for (Node node : nowe_bloki) {
                        Rectangle a = (Rectangle) node;
                        if (a.getY() < linestodelete.get(0) * Tetris.SIZE) {
                            Tetris.GRID[(int) a.getX() / Tetris.SIZE][(int) a.getY() / Tetris.SIZE] = 0;
                            a.setY(a.getY() + Tetris.SIZE);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }

                linestodelete.remove(0);
                bloki.clear();
                nowe_bloki.clear();

                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle) {
                        if (node != prevblock1 && node != prevblock2 && node != prevblock3 && node != prevblock4)
                            bloki.add(node);
                    }
                }
                for (Node node : bloki) {
                    Rectangle a = (Rectangle) node;
                    try {
                        Tetris.GRID[(int) a.getX() / Tetris.SIZE][(int) a.getY() / Tetris.SIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                bloki.clear();
            } while (linestodelete.size() > 0);
    }


    /**
     * Method that makes block fall and controls how many blocks are already on the scene.
     * @param block controlled block
     */

    public static void blockFall(Block block) {

        if (block.a.getY() == YMAX - SIZE || block.b.getY() == YMAX - SIZE || block.c.getY() == YMAX - SIZE
                || block.d.getY() == YMAX - SIZE || canrectangleAMove(block) || canrectangleBMove(block) || canrectangleCMove(block) || canrectangleDMove(block)) {
            GRID[(int) block.a.getX() / SIZE][(int) block.a.getY() / SIZE] = 1;
            GRID[(int) block.b.getX() / SIZE][(int) block.b.getY() / SIZE] = 1;
            GRID[(int) block.c.getX() / SIZE][(int) block.c.getY() / SIZE] = 1;
            GRID[(int) block.d.getX() / SIZE][(int) block.d.getY() / SIZE] = 1;
            keyController.removeObserver(block);
            deleteRow(gamepane);
            Block a = nextblock;
            nextblock = Controller.createBlock();
            Block newblock = nextblock;
            currentblock = a;
            gamepane.getChildren().addAll(a.a, a.b, a.c, a.d);
            keyController.addObserver(a);
            MoveOnKeyPress();
            Rectangle one = new Rectangle(SIZE - 1, SIZE - 1);
            Rectangle two = new Rectangle(SIZE - 1, SIZE - 1);
            Rectangle three = new Rectangle(SIZE - 1, SIZE - 1);
            Rectangle four = new Rectangle(SIZE - 1, SIZE - 1);
            gamepane.getChildren().removeAll(prevblock1, prevblock2, prevblock3, prevblock4);
            prevblock1 = one;
            prevblock2 = two;
            prevblock3 = three;
            prevblock4 = four;

            shownextblock(one, two, three, four, newblock.getName(), gamepane);

        }

        if (block.a.getY() + MOVEMENT < YMAX && block.b.getY() + MOVEMENT < YMAX && block.c.getY() + MOVEMENT < YMAX
                && block.d.getY() + MOVEMENT < YMAX) {
            int rusza = GRID[(int) block.a.getX() / SIZE][((int) block.a.getY() / SIZE) + 1];
            int ruszb = GRID[(int) block.b.getX() / SIZE][((int) block.b.getY() / SIZE) + 1];
            int ruszc = GRID[(int) block.c.getX() / SIZE][((int) block.c.getY() / SIZE) + 1];
            int ruszd = GRID[(int) block.d.getX() / SIZE][((int) block.d.getY() / SIZE) + 1];
            if (rusza == 0 && rusza == ruszb && ruszb == ruszc && ruszc == ruszd) {
                block.a.setY(block.a.getY() + MOVEMENT);
                block.b.setY(block.b.getY() + MOVEMENT);
                block.c.setY(block.c.getY() + MOVEMENT);
                block.d.setY(block.d.getY() + MOVEMENT);
            }

        }
    }


    private static boolean canrectangleAMove(Block block) {
        return (GRID[(int) block.a.getX() / SIZE][(int) block.a.getY() / SIZE + 1] == 1);
    }

    private static boolean canrectangleBMove(Block block) {
        return (GRID[(int) block.b.getX() / SIZE][(int) block.b.getY() / SIZE + 1] == 1);
    }


    private static boolean canrectangleCMove(Block block) {
        return (GRID[(int) block.c.getX() / SIZE][(int) block.c.getY() / SIZE + 1] == 1);
    }


    private static boolean canrectangleDMove(Block block) {
        return (GRID[(int) block.d.getX() / SIZE][(int) block.d.getY() / SIZE + 1] == 1);
    }


    /**
     * Method showing next block on the right of the stage
     * @param a rectangle a of next block
     * @param b rectangle b of next block
     * @param c rectangle c of next block
     * @param d rectangle d of next block
     * @param name name of next block
     * @param pane  gamepane
     */


    public static void shownextblock(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, Pane pane) {
        Stop[] stops;
        LinearGradient linearGradient = null;
        switch (name) {
            case "j":
                stops = new Stop[]{new Stop(0,Color.valueOf("#0b4eb3")),
                        new Stop(1, Color.valueOf("#cc00cc"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(175);
                c.setY(200);
                d.setY(200);
                a.setX(XMAX + 40);
                b.setX(XMAX + 40);
                c.setX(XMAX + 40);
                d.setX(XMAX + 15);
                break;
            case "l":
                stops = new Stop[]{new Stop(0,Color.valueOf("#e33454")),
                        new Stop(1, Color.valueOf("#ff3e30"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(175);
                c.setY(200);
                d.setY(200);
                a.setX(XMAX + 40);
                b.setX(XMAX + 40);
                c.setX(XMAX + 40);
                d.setX(XMAX + 65);
                break;
            case "o":
                stops = new Stop[]{new Stop(0,Color.valueOf("#ffa142")),
                        new Stop(1, Color.valueOf("#D14D00"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(150);
                c.setY(175);
                d.setY(175);
                a.setX(XMAX + 40);
                b.setX(XMAX + 15);
                c.setX(XMAX + 40);
                d.setX(XMAX + 15);
                break;
            case "s":
                stops = new Stop[]{new Stop(0,Color.valueOf("#06700d")),
                        new Stop(1, Color.valueOf("#78ad1c"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(150);
                c.setY(175);
                d.setY(175);
                a.setX(XMAX + 65);
                b.setX(XMAX + 40);
                c.setX(XMAX + 40);
                d.setX(XMAX + 15);
                break;
            case "z":
                stops = new Stop[]{new Stop(0.7,Color.valueOf("#FFF41C")),
                        new Stop(0.3, Color.valueOf("#ffe44d"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(150);
                c.setY(175);
                d.setY(175);
                a.setX(XMAX + 15);
                b.setX(XMAX + 40);
                c.setX(XMAX + 40);
                d.setX(XMAX + 65);
                break;
            case "i":
                stops = new Stop[]{new Stop(0,Color.valueOf("#0D62FF")),
                        new Stop(1, Color.valueOf("#57aeff"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(175);
                c.setY(200);
                d.setY(225);
                a.setX(XMAX + 40);
                b.setX(XMAX + 40);
                c.setX(XMAX + 40);
                d.setX(XMAX + 40);
                break;
            case "t":
                stops = new Stop[]{new Stop(0,Color.valueOf("#6600ff")),
                        new Stop(1, Color.valueOf("#ff00ff"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                a.setY(150);
                b.setY(175);
                c.setY(175);
                d.setY(175);
                a.setX(XMAX + 40);
                b.setX(XMAX + 40);
                c.setX(XMAX + 15);
                d.setX(XMAX + 65);
                break;
        }
        a.setFill(linearGradient);
        b.setFill(linearGradient);
        c.setFill(linearGradient);
        d.setFill(linearGradient);
        pane.getChildren().addAll(a, b, c, d);


    }


}
