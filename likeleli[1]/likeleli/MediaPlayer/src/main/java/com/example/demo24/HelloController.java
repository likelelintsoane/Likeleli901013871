package com.example.demo24;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

import static javafx.scene.media.MediaPlayer.Status.PLAYING;


public class HelloController {

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider mediaSlider;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Label timeLabel;
    @FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private BorderPane borderPane;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    void openfile(ActionEvent even) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File");

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {


            String path = file.getAbsolutePath();


            Media media = new Media(new File(path).toURI().toString());


            mediaPlayer  = new MediaPlayer(media);


            mediaPlayer.setAutoPlay(true);



            mediaView.setMediaPlayer(mediaPlayer);

            // bind the MediaView's size to the Scene's size
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));


            // set up the media slider
            mediaPlayer.setOnReady(() -> {
                mediaSlider.setMin(0);
                mediaSlider.setMax(mediaPlayer.getMedia().getDuration().toSeconds());

                mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                    mediaSlider.setValue(newValue.toSeconds());
                    updateTimeLabel();
                });
            });
            // set up the volume slider
            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                mediaPlayer.setVolume(newValue.doubleValue() / 100);
            });
            }

        }
    @FXML
    private void updateTimeLabel() {
        Duration currentTime = mediaPlayer.getCurrentTime();
        Duration totalTime = mediaPlayer.getTotalDuration();

        String currentTimeFormatted = formatTime(currentTime);
        String totalTimeFormatted = formatTime(totalTime);

        timeLabel.setText(currentTimeFormatted + " / " + totalTimeFormatted);
    }

    private String formatTime(Duration duration) {
        int minutes = (int) duration.toMinutes();
        int seconds = (int) (duration.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }

    @FXML
    void pause(ActionEvent event) {
        if(mediaPlayer.getStatus() == PLAYING){
            mediaPlayer.pause();
        }else{

            mediaPlayer.play();

        }

    }

    @FXML
    void slow(ActionEvent event) {
        mediaPlayer.setRate(mediaPlayer.getRate()-0.5);

    }
    @FXML
    void fast(ActionEvent event) {
        mediaPlayer.setRate(mediaPlayer.getRate()+0.5);

    }
    @FXML
    void close(ActionEvent event) {
        System.exit(0);

    }
    @FXML
    void selectColor(ActionEvent event) {
        borderPane.setStyle("-fx-background-color: " + colorPicker.getValue().toString().replace("0x", "#") + ";");

    }

}

