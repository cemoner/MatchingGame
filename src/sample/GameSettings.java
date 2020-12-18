package sample;


import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameSettings extends View {

    Integer openedCards = 0;
    ArrayList<ImageView> imageViews = new ArrayList<>();
    ArrayList<Image> openedCardsList = new ArrayList<>();
    ArrayList<ImageView> openedCardsImageViews = new ArrayList<>();
    ArrayList<Image> imageArrayList = new ArrayList<>();


    public void addingListeners(ImageView imageView, Image image) {
        imageView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> {
            imageView.setDisable(true);
            openedCards += 1;
            openedCardsList.add(image);
            openedCardsImageViews.add(imageView);
            transitionAnimation(imageView, image);

            if (openedCards == 2) {
                checkIfMatched();
            }
        });
    }

    public void checkIfMatched() {
        openedCards = 0;
        for (ImageView imageView: imageViews) {
            imageView.setDisable(true);
        }
        if (openedCardsList.get(0) == openedCardsList.get(1)) {
            imageViews.remove(openedCardsImageViews.get(0));
            imageViews.remove(openedCardsImageViews.get(1));
            openedCardsImageViews.get(0).setDisable(true);
            openedCardsImageViews.get(1).setDisable(true);

            for (ImageView imageView:imageViews) {
                imageView.setDisable(false);
            }


        } else {
            PauseAndThenClose(openedCardsImageViews.get(0),imageArrayList.get(52));
            PauseAndThenClose(openedCardsImageViews.get(1),imageArrayList.get(52));
        }

        openedCardsList.remove(0);
        openedCardsList.remove(0);
        openedCardsImageViews.remove(0);
        openedCardsImageViews.remove(0);
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
            for(ImageView imageView1:imageViews){
                imageView1.setDisable(false);
            }
        });

        back.setFromX(0);
        back.setToX(1);
        back.setCycleCount(1);

        new SequentialTransition(pauseTransition, front, back).play();

    }

}
