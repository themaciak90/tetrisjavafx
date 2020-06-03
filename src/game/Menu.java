package game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * Class that creates the menu and music
 */

public class Menu {
    private static Pane menu = new Pane();

    private static Scene menuscene = new Scene(menu, Tetris.XMAX + 150, Tetris.YMAX);
    private Image logo;
    private Button startbutton;
    private Button exitbutton;


    /**
     * Method that creates the menu scene
     * @param stage game window
     * @param gamepane pane of the game
     * @param gamescene scene of the game
     */

    public void createmenu(Stage stage, Pane gamepane, Scene gamescene){
        logo = new Image(getClass().getResourceAsStream("logo.png"));
        ImageView logoshow = new ImageView(logo);
        logoshow.setX(110);
        logoshow.setY(30);
        startbutton = new Button("START");
        startbutton.getStyleClass().clear();
        startbutton.getStylesheets().add("Button.css");
        startbutton.getStyleClass().add("button");

        exitbutton = new Button("Wyjscie");
        exitbutton.getStyleClass().clear();
        exitbutton.getStylesheets().add("Button.css");
        exitbutton.getStyleClass().add("button");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startbutton.setEffect(new DropShadow());
                stage.setScene(gamescene);
                Tetris.isRunning = true;
                playmusic(gamepane);

            }
        };
        startbutton.setMinSize(100, 50);
        startbutton.setLayoutX(170);
        startbutton.setLayoutY(250);
        exitbutton.setMinSize(100, 50);
        exitbutton.setLayoutX(170);
        exitbutton.setLayoutY(325);
        startbutton.setOnAction(event);
        exitbutton.setOnAction(e -> System.exit(0));
        menu.setStyle("-fx-background-color: linear-gradient(#cb00ff, #4b53ff)");
        menu.getChildren().addAll(logoshow, startbutton, exitbutton);
        stage.setScene(menuscene);

    }


    /**
     * Method that plays the music
     * @param pane pane of the game
     */

    public void playmusic(Pane pane) {

        String musicfile = "Muzyka.mp3";
        Media sound = new Media(new File(musicfile).toURI().toString());
        MediaPlayer player = new MediaPlayer(sound);
        player.seek(Duration.ZERO);
        player.setVolume(0.03);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);

        Button mute = new Button("Wyjscie");
        mute.setLayoutX(Tetris.XMAX + 5);
        mute.setLayoutY(500);
        mute.setMaxSize(50, 50);
        mute.getStyleClass().clear();
        mute.getStylesheets().add("Button.css");
        mute.getStylesheets().add("Button.css");
        mute.getStyleClass().add("button");
        mute.setText("Wycisz");
        mute.setMinSize(100, 50);
        EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!player.isMute()){
                    player.setMute(true);
                    player.setVolume(0);
                    mute.setMaxSize(50, 50);
                    mute.setEffect(new DropShadow());
                }
                else
                {
                    player.setMute(false);
                    player.setVolume(0.03);
                    mute.setMaxSize(50, 50);
                    mute.setEffect(null);
                }
            }
        };
        mute.setOnAction(ev);



        pane.getChildren().add(mute);

    }
}
