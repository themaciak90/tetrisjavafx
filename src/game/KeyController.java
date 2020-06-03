package game;


import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing KeyPressed. This class is the subject of model observer
 */

public class KeyController implements KeyPressed {
    List<ObserveKeyPressed> observators;

    public KeyController(){
        this.observators = new ArrayList<>();
    }

    /**
     * Method that adds observer to collection
     * @param observator observer to add
     */
    @Override
    public void addObserver(ObserveKeyPressed observator) {
        observators.add(observator);
    }

    /**
     * Method that removes observer from collection
     * @param observator observer to remove
     */
    @Override
    public void removeObserver(ObserveKeyPressed observator) {
        observators.remove(observator);
    }


    /**
     * Method that notifies observers
     * @param keyEvent KeyEvent is passed to observers
     */
    @Override
    public void notifyObservers(KeyEvent keyEvent) {
        for(ObserveKeyPressed observator : observators){
            observator.update(keyEvent);
        }
    }
}
