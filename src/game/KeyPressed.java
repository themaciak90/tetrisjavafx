package game;

import javafx.scene.input.KeyEvent;

/**
 * Interface for subject in model observer
 */

public interface KeyPressed {
    void addObserver(ObserveKeyPressed observator);
    void removeObserver(ObserveKeyPressed observator);
    void notifyObservers(KeyEvent keyEvent);
}
