package sample;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class View implements Initializable {
    @FXML
    private ImageView imageView16;

    @FXML
    private ImageView imageView15;

    @FXML
    private ImageView imageView9;

    @FXML
    private ImageView imageView14;

    @FXML
    private ImageView imageView8;

    @FXML
    private ImageView imageView13;

    @FXML
    private ImageView imageView12;

    @FXML
    private ImageView imageView11;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView7;

    @FXML
    private ImageView imageView6;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView10;

    @FXML
    private Button startButton;

    @FXML
    Button shuffleButton;

    List<Image> imageList = new ArrayList<>();
    List<Image> pickedImagesOfImageViews = new ArrayList<>();
    List<ImageView> imageViewList = new ArrayList<>();
    List<Image> randomImages = new ArrayList<>();
    GameSettings gameSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setImageList();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setImageViewList();
        try {
            pickImages();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        startButton.setDisable(true);
        gameSettings = new GameSettings();
    }

    public void setImageList() throws MalformedURLException {
        imageList.clear();
        for (int i = 1; i <= 53; i++) {
            String location = "C:\\Users\\cemon\\IdeaProjects\\MatchingGame\\src\\Images\\" + i + ".jpg";
            imageList.add(new Image(new File(location).toURI().toURL().toExternalForm()));
        }
    }

    public void setImageViewList() {
        imageViewList.clear();
        imageViewArrangements(imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8);
        imageViewArrangements(imageView9, imageView10, imageView11, imageView12, imageView13, imageView14, imageView15, imageView16);
    }

    private void imageViewArrangements(ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, ImageView imageView6, ImageView imageView7, ImageView imageView8) {
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        imageViewList.add(imageView4);
        imageViewList.add(imageView5);
        imageViewList.add(imageView6);
        imageViewList.add(imageView7);
        imageViewList.add(imageView8);
    }

    public void setRandomImagesFromDeck() {
        int counter = 0;
        int size = 15;
        for (int i = 0; i <= 7; ) {
            if (counter < 2) {
                int randomImageView = (int) (Math.random() * size);
                counter += 1;
                size -= 1;
                transitionAnimation(imageViewList.get(randomImageView), randomImages.get(i));
                imageViewList.remove(randomImageView);
            }
            if (counter == 2) {
                counter = 0;
                i++;
            }
        }
        setImageViewList();
        startButton.setDisable(false);
    }

    public void pickImages() throws MalformedURLException {
        for (int i = 51; i >= 44; i--) {
            int randomIndex = (int) (Math.random() * i);
            randomImages.add(imageList.get(randomIndex));
            imageList.remove(randomIndex);
        }
        setImageList();
    }

    public void transitionAnimation(ImageView imageView, Image image) {
        ScaleTransition front = new ScaleTransition(Duration.seconds(0.15), imageView);
        ScaleTransition back = new ScaleTransition(Duration.seconds(0.15), imageView);

        front.setFromX(1);
        front.setToX(0);
        front.setCycleCount(1);

        front.setOnFinished(e -> {
            imageView.setImage(image);
        });

        back.setFromX(0);
        back.setToX(1);
        back.setCycleCount(1);

        new SequentialTransition(front, back).play();
    }

    public void PauseAndThenClose(ImageView imageView, Image image) {
        ScaleTransition front = new ScaleTransition(Duration.seconds(0.15), imageView);
        ScaleTransition back = new ScaleTransition(Duration.seconds(0.15), imageView);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.6));

        front.setFromX(1);
        front.setToX(0);
        front.setCycleCount(1);

        front.setOnFinished(e -> {
            imageView.setImage(image);
            for(ImageView imageView1:gameSettings.imageViews){
                imageView1.setDisable(false);
            }
        });

        back.setFromX(0);
        back.setToX(1);
        back.setCycleCount(1);

        new SequentialTransition(pauseTransition, front, back).play();

    }

    public void closeCards() {
        for (int i = 0; i <= 15; i++) {
            transitionAnimation(imageViewList.get(i), imageList.get(52));
            startButton.setDisable(true);
            shuffleButton.setDisable(true);
        }
        setPickedImagesOfImageViews();

        gameSettings.imageViews.addAll(imageViewList);
        gameSettings.imageArrayList.addAll(imageList);

        for (int i = 0; i <= 15; i++) {
            gameSettings.addingListeners(imageViewList.get(i), pickedImagesOfImageViews.get(i));
        }
    }

    public void setPickedImagesOfImageViews() {
        for (int i = 0; i <= 15; i++) {
            pickedImagesOfImageViews.add(imageViewList.get(i).getImage());
        }
    }

}
