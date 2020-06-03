package game;

import javafx.scene.shape.*;

/**
 * Class controlling blocks and creating them.
 */



public class Controller {
    //pobieranie zmiennych z klasy Tetris
    public static final int MOVEMENT = Tetris.MOVEMENT;

    public static final int SIZE = Tetris.SIZE;

    public static int XMAX = Tetris.XMAX;

    public static int YMAX = Tetris.YMAX;

    public static int [][] GRID = Tetris.GRID;


    //ruch klockami

    /**
     * Method that makes block to move right
     * @param block Controlled block by the player
     */

    public static void moveblockRight(Block block){
        if(block.a.getX() + MOVEMENT <= XMAX - SIZE && block.b.getX() + MOVEMENT <= XMAX - SIZE &&
                block.c.getX() + MOVEMENT <= XMAX - SIZE && block.d.getX() + MOVEMENT <= XMAX - SIZE){
            int rectangle_a = GRID[((int) block.a.getX()/ SIZE) + 1][((int) block.a.getY()/ SIZE)];
            int rectangle_b = GRID[((int) block.b.getX()/ SIZE) + 1][((int) block.b.getY()/ SIZE)];
            int rectangle_c = GRID[((int) block.c.getX()/ SIZE) + 1][((int) block.c.getY()/ SIZE)];
            int rectangle_d = GRID[((int) block.d.getX()/ SIZE) + 1][((int) block.d.getY()/ SIZE)];

            if(rectangle_a == 0 && rectangle_a == rectangle_b && rectangle_b == rectangle_c && rectangle_c == rectangle_d)
            {
                block.a.setX(block.a.getX() + MOVEMENT);
                block.b.setX(block.b.getX() + MOVEMENT);
                block.c.setX(block.c.getX() + MOVEMENT);
                block.d.setX(block.d.getX() + MOVEMENT);
            }
        }
    }

    /**
     * Method that makes block to move left
     * @param block Controlled block by the player
     */


    //ruch w lewo tak samo
    public static void moveblockLeft(Block block){
        if(block.a.getX() - MOVEMENT >= 0 && block.b.getX() - MOVEMENT >= 0 &&
                block.c.getX() - MOVEMENT >= 0 && block.d.getX() - MOVEMENT >= 0){
            int rectangle_a = GRID[((int) block.a.getX()/ SIZE) - 1][((int) block.a.getY()/ SIZE)];
            int rectangle_b = GRID[((int) block.b.getX()/ SIZE) - 1][((int) block.b.getY()/ SIZE)];
            int rectangle_c = GRID[((int) block.c.getX()/ SIZE) - 1][(int) block.c.getY()/ SIZE];
            int rectangle_d = GRID[((int) block.d.getX()/ SIZE) - 1][((int) block.d.getY()/ SIZE)];

            if(rectangle_a == 0 && rectangle_a == rectangle_b && rectangle_b == rectangle_c && rectangle_c == rectangle_d)
            {
                block.a.setX(block.a.getX() - MOVEMENT);
                block.b.setX(block.b.getX() - MOVEMENT);
                block.c.setX(block.c.getX() - MOVEMENT);
                block.d.setX(block.d.getX() - MOVEMENT);
            }
        }
    }


    /**
     * Method that creates next random block.
     * @return Block
     */


    public static Block createBlock(){
        //losowy  klocek
        int random = (int)(Math.random()*100);
        String name;

        Rectangle a = new Rectangle(SIZE -1, SIZE -1),
                b = new Rectangle(SIZE -1, SIZE -1),
                c = new Rectangle(SIZE -1, SIZE -1),
                d = new Rectangle(SIZE -1, SIZE -1);

        if(random < 15){
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX /2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX/2 + SIZE);
            d.setY(SIZE);
            name = "j";
        }
        else if(random < 30){
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "l";
        }
        else if(random < 45){
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
            name = "o";
        }
        else if (random < 60) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE);
            name = "s";
        }
        else if (random < 75){
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            name = "t";
        }
        else if (random < 90){
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 + SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE + SIZE);
            d.setY(SIZE);
            name = "z";
        }
        else{
            a.setX(XMAX / 2 - SIZE - SIZE);
            b.setX(XMAX / 2 - SIZE);
            c.setX(XMAX / 2);
            d.setX(XMAX / 2 + SIZE);
            name = "i";
        }
        return new Block(a, b, c, d, name);
    }

}
