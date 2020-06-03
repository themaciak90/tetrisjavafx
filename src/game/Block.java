package game;

import com.sun.javafx.css.Style;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;

/**
 * Class that creates blocks. Class is also observer in observer pattern.
 */


public class Block implements ObserveKeyPressed{

    public static final int MOVEMENT = Tetris.MOVEMENT;

    public static final int SIZE = Tetris.SIZE;

    public static int XMAX = Tetris.XMAX;

    public static int YMAX = Tetris.YMAX;

    public static int [][] GRID = Tetris.GRID;


    Rectangle a;

    Rectangle b;

    Rectangle c;

    Rectangle d;

    Color color;


    private String name;

    public int currentshape = 1;


    public Block(Rectangle a, Rectangle b, Rectangle c, Rectangle d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }



    public Block(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;
        Stop[] stops;
        LinearGradient linearGradient = null;


        //kolory
        switch (name) {
            case "j":
                stops = new Stop[]{new Stop(0,Color.valueOf("#0b4eb3")),
                        new Stop(1, Color.valueOf("#cc00cc"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.BLUEVIOLET;
                break;
            case "l":
                stops = new Stop[]{new Stop(0,Color.valueOf("#e33454")),
                        new Stop(1, Color.valueOf("#ff3e30"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.ORANGERED;
                break;
            case "o":
                stops = new Stop[]{new Stop(0,Color.valueOf("#ffa142")),
                        new Stop(1, Color.valueOf("#D14D00"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.FORESTGREEN;
                break;
            case "s":
                stops = new Stop[]{new Stop(0,Color.valueOf("#06700d")),
                        new Stop(1, Color.valueOf("#78ad1c"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.PINK;
                break;
            case "t":
                stops = new Stop[]{new Stop(0,Color.valueOf("#6600ff")),
                        new Stop(1, Color.valueOf("#ff00ff"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.YELLOW;
                break;
            case "z":
                stops = new Stop[]{new Stop(0.7,Color.valueOf("#FFF41C")),
                        new Stop(0.3, Color.valueOf("#ffe44d"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.SANDYBROWN;
                break;
            case "i":
                stops = new Stop[]{new Stop(0,Color.valueOf("#0D62FF")),
                        new Stop(1, Color.valueOf("#57aeff"))};
                linearGradient = new LinearGradient(0,0,1,0,true,
                        CycleMethod.NO_CYCLE,stops);
                color = Color.AQUA;
                break;
        }
        this.a.setFill(linearGradient);
        this.b.setFill(linearGradient);
        this.c.setFill(linearGradient);
        this.d.setFill(linearGradient);

    }

    public String getName() {
        return name;
    }


    public void changeShape(){
        if(currentshape != 4)
            currentshape++;
        else
            currentshape = 1;
    }

    /**
     * Method that controls the block.
     * @param keyEvent Object defining what block has to do.
     */

    @Override
    public void update(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case RIGHT:
                Controller.moveblockRight(this);
                break;
            case LEFT:
                Controller.moveblockLeft(this);
                break;
            case UP:
                turnBlock(this);
                break;
            case DOWN:
                Tetris.blockFall(this);
                Tetris.score++;
                break;
        }

    }


    private void turnBlock(Block block) {
        int f = block.currentshape;
        Rectangle a = block.a;
        Rectangle b = block.b;
        Rectangle c = block.c;
        Rectangle d = block.d;
        switch (block.getName()) {
            case "j":
                if (f == 1 && isagainstBorder(a, 1, -1) && isagainstBorder(c, -1, -1) && isagainstBorder(d, -2, -2)) {
                    moverectangleRight(block.a);
                    moverectangleDown(block.a);
                    moverectangleDown(block.c);
                    moverectangleLeft(block.c);
                    moverectangleDown(block.d);
                    moverectangleDown(block.d);
                    moverectangleLeft(block.d);
                    moverectangleLeft(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 2 && isagainstBorder(a, -1, -1) && isagainstBorder(c, -1, 1) && isagainstBorder(d, -2, 2)) {
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    moverectangleLeft(block.d);
                    moverectangleLeft(block.d);
                    movrectangleUp(block.d);
                    movrectangleUp(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 3 && isagainstBorder(a, -1, 1) && isagainstBorder(c, 1, 1) && isagainstBorder(d, 2, 2)) {
                    moverectangleLeft(block.a);
                    movrectangleUp(block.a);
                    movrectangleUp(block.c);
                    moverectangleRight(block.c);
                    movrectangleUp(block.d);
                    movrectangleUp(block.d);
                    moverectangleRight(block.d);
                    moverectangleRight(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 4 && isagainstBorder(a, 1, 1) && isagainstBorder(c, 1, -1) && isagainstBorder(d, 2, -2)) {
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    moverectangleRight(block.d);
                    moverectangleRight(block.d);
                    moverectangleDown(block.d);
                    moverectangleDown(block.d);
                    block.changeShape();
                    break;
                }
                break;
            case "l":
                if (f == 1 && isagainstBorder(a, 1, -1) && isagainstBorder(c, 1, 1) && isagainstBorder(b, 2, 2)) {
                    moverectangleRight(block.a);
                    moverectangleDown(block.a);
                    movrectangleUp(block.c);
                    moverectangleRight(block.c);
                    movrectangleUp(block.b);
                    movrectangleUp(block.b);
                    moverectangleRight(block.b);
                    moverectangleRight(block.b);
                    block.changeShape();
                    break;
                }
                if (f == 2 && isagainstBorder(a, -1, -1) && isagainstBorder(b, 2, -2) && isagainstBorder(c, 1, -1)) {
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    moverectangleRight(block.b);
                    moverectangleRight(block.b);
                    moverectangleDown(block.b);
                    moverectangleDown(block.b);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    block.changeShape();
                    break;
                }
                if (f == 3 && isagainstBorder(a, -1, 1) && isagainstBorder(c, -1, -1) && isagainstBorder(b, -2, -2)) {
                    moverectangleLeft(block.a);
                    movrectangleUp(block.a);
                    moverectangleDown(block.c);
                    moverectangleLeft(block.c);
                    moverectangleDown(block.b);
                    moverectangleDown(block.b);
                    moverectangleLeft(block.b);
                    moverectangleLeft(block.b);
                    block.changeShape();
                    break;
                }
                if (f == 4 && isagainstBorder(a, 1, 1) && isagainstBorder(b, -2, 2) && isagainstBorder(c, -1, 1)) {
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleLeft(block.b);
                    moverectangleLeft(block.b);
                    movrectangleUp(block.b);
                    movrectangleUp(block.b);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    block.changeShape();
                    break;
                }
                break;
            case "o":
                break;
            case "s":
                if (f == 1 && isagainstBorder(a, -1, -1) && isagainstBorder(c, -1, 1) && isagainstBorder(d, 0, 2)) {
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    movrectangleUp(block.d);
                    movrectangleUp(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 2 && isagainstBorder(a, 1, 1) && isagainstBorder(c, 1, -1) && isagainstBorder(d, 0, -2)) {
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    moverectangleDown(block.d);
                    moverectangleDown(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 3 && isagainstBorder(a, -1, -1) && isagainstBorder(c, -1, 1) && isagainstBorder(d, 0, 2)) {
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    movrectangleUp(block.d);
                    movrectangleUp(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 4 && isagainstBorder(a, 1, 1) && isagainstBorder(c, 1, -1) && isagainstBorder(d, 0, -2)) {
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    moverectangleDown(block.d);
                    moverectangleDown(block.d);
                    block.changeShape();
                    break;
                }
                break;
            case "t":
                if (f == 1 && isagainstBorder(a, 1, 1) && isagainstBorder(d, -1, -1) && isagainstBorder(c, -1, 1)) {
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleDown(block.d);
                    moverectangleLeft(block.d);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    block.changeShape();
                    break;
                }
                if (f == 2 && isagainstBorder(a, 1, -1) && isagainstBorder(d, -1, 1) && isagainstBorder(c, 1, 1)) {
                    moverectangleRight(block.a);
                    moverectangleDown(block.a);
                    moverectangleLeft(block.d);
                    movrectangleUp(block.d);
                    movrectangleUp(block.c);
                    moverectangleRight(block.c);
                    block.changeShape();
                    break;
                }
                if (f == 3 && isagainstBorder(a, -1, -1) && isagainstBorder(d, 1, 1) && isagainstBorder(c, 1, -1)) {
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    movrectangleUp(block.d);
                    moverectangleRight(block.d);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    block.changeShape();
                    break;
                }
                if (f == 4 && isagainstBorder(a, -1, 1) && isagainstBorder(d, 1, -1) && isagainstBorder(c, -1, -1)) {
                    moverectangleLeft(block.a);
                    movrectangleUp(block.a);
                    moverectangleRight(block.d);
                    moverectangleDown(block.d);
                    moverectangleDown(block.c);
                    moverectangleLeft(block.c);
                    block.changeShape();
                    break;
                }
                break;
            case "z":
                if (f == 1 && isagainstBorder(b, 1, 1) && isagainstBorder(c, -1, 1) && isagainstBorder(d, -2, 0)) {
                    movrectangleUp(block.b);
                    moverectangleRight(block.b);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    moverectangleLeft(block.d);
                    moverectangleLeft(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 2 && isagainstBorder(b, -1, -1) && isagainstBorder(c, 1, -1) && isagainstBorder(d, 2, 0)) {
                    moverectangleDown(block.b);
                    moverectangleLeft(block.b);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    moverectangleRight(block.d);
                    moverectangleRight(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 3 && isagainstBorder(b, 1, 1) && isagainstBorder(c, -1, 1) && isagainstBorder(d, -2, 0)) {
                    movrectangleUp(block.b);
                    moverectangleRight(block.b);
                    moverectangleLeft(block.c);
                    movrectangleUp(block.c);
                    moverectangleLeft(block.d);
                    moverectangleLeft(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 4 && isagainstBorder(b, -1, -1) && isagainstBorder(c, 1, -1) && isagainstBorder(d, 2, 0)) {
                    moverectangleDown(block.b);
                    moverectangleLeft(block.b);
                    moverectangleRight(block.c);
                    moverectangleDown(block.c);
                    moverectangleRight(block.d);
                    moverectangleRight(block.d);
                    block.changeShape();
                    break;
                }
                break;
            case "i":
                if (f == 1 && isagainstBorder(a, 2, 2) && isagainstBorder(b, 1, 1) && isagainstBorder(d, -1, -1)) {
                    movrectangleUp(block.a);
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleRight(block.a);
                    movrectangleUp(block.b);
                    moverectangleRight(block.b);
                    moverectangleDown(block.d);
                    moverectangleLeft(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 2 && isagainstBorder(a, -2, -2) && isagainstBorder(b, -1, -1) && isagainstBorder(d, 1, 1)) {
                    moverectangleDown(block.a);
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    moverectangleLeft(block.a);
                    moverectangleDown(block.b);
                    moverectangleLeft(block.b);
                    movrectangleUp(block.d);
                    moverectangleRight(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 3 && isagainstBorder(a, 2, 2) && isagainstBorder(b, 1, 1) && isagainstBorder(d, -1, -1)) {
                    movrectangleUp(block.a);
                    movrectangleUp(block.a);
                    moverectangleRight(block.a);
                    moverectangleRight(block.a);
                    movrectangleUp(block.b);
                    moverectangleRight(block.b);
                    moverectangleDown(block.d);
                    moverectangleLeft(block.d);
                    block.changeShape();
                    break;
                }
                if (f == 4 && isagainstBorder(a, -2, -2) && isagainstBorder(b, -1, -1) && isagainstBorder(d, 1, 1)) {
                    moverectangleDown(block.a);
                    moverectangleDown(block.a);
                    moverectangleLeft(block.a);
                    moverectangleLeft(block.a);
                    moverectangleDown(block.b);
                    moverectangleLeft(block.b);
                    movrectangleUp(block.d);
                    moverectangleRight(block.d);
                    block.changeShape();
                    break;
                }
                break;
        }

    }
    private void moverectangleDown(Rectangle rec) {
        if (rec.getY() + MOVEMENT < YMAX)
            rec.setY(rec.getY() + MOVEMENT);
    }


    private void moverectangleRight(Rectangle rec) {
        if (rec.getX() + MOVEMENT <= XMAX - SIZE)
            rec.setX(rec.getX() + MOVEMENT);
    }



    private void moverectangleLeft(Rectangle rec) {
        if (rec.getX() - MOVEMENT >= 0)
            rec.setX(rec.getX() - MOVEMENT);
    }


    private void movrectangleUp(Rectangle rec) {
        if (rec.getY() + MOVEMENT > 0)
            rec.setY(rec.getY() - MOVEMENT);
    }

    private boolean isagainstBorder(Rectangle rect, int x, int y) {
        boolean yb = false;
        boolean xb = false;
        try {
            if (x >= 0)
                xb = rect.getX() + x * MOVEMENT <= XMAX - SIZE;
            if (x < 0)
                xb = rect.getX() + x * MOVEMENT >= 0;
            if (y >= 0)
                yb = rect.getY() - y * MOVEMENT > 0;
            if (y < 0)
                yb = rect.getY() + y * MOVEMENT < YMAX;
        }catch (ArrayIndexOutOfBoundsException e){}

        return xb && yb && GRID[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;

    }


}
