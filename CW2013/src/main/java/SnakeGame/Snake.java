package SnakeGame;


// very useless class (delete) | Make another MySnake class based on what we have in 'MyFrame'.
public class Snake {
	
		private static final long serialVersionUID = -3641221053272056036L;


    // TODO: it needs a refresh

    public static int moving;
    public static int move(int x) {
        moving = x;
        return moving;
    }

    public static void stop() {
        moving = 0;
    }
}
