package SnakeGame;

//
public class SnakeGameApp {
    public static void main(String[] args) {
        // create Model
        Model model = new Model();

        // create Controller + pass model
        Controller controller = new Controller(model);

        // create View
        View view = new View();
        view.initialize(model, controller);

        // set View in Controller
        controller.setView(view);

        // Load the frame and start the game
        view.loadFrame();
        MusicPlayer.getMusicPlay("src/main/resources/frogger.mp3");

    }
}