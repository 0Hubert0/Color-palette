package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final boolean[] isPressed = {false};
        final double[] x = new double[1];
        final double[] y = new double[1];
        final Color[] color = {null};
        final double[] yControl = new double[1];
        final double[] xControl = new double[1];
        final double[] red = {0};
        final double[] green = {0};
        final double[] blue = {0};

        AnchorPane root = new AnchorPane();

        Scene scene = new Scene(root, 1300, 900);

        Rectangle background = new Rectangle(scene.getWidth(), scene.getHeight());
        Rectangle control = new Rectangle(scene.getWidth()*0.3, scene.getHeight()*0.3);
        control.setLayoutX(scene.getWidth()/3);
        control.setLayoutY(scene.getHeight()/3);
        control.setStroke(Color.BLACK);

        control.setOnMouseEntered(event -> root.setCursor(Cursor.HAND));
        control.setOnMouseExited(event -> root.setCursor(Cursor.DEFAULT));
        control.setOnMouseReleased(event -> root.setCursor(Cursor.HAND));
        control.setOnMouseMoved(event -> {
            xControl[0] = event.getX();
            yControl[0] = event.getY();

        });
        control.setOnMousePressed(event -> {
            isPressed[0] = true;
            x[0] = event.getX();
            y[0] = event.getY();
        });
        background.setOnMouseReleased(event -> {
            isPressed[0] = false;
            root.setCursor(Cursor.DEFAULT);
        });
        scene.setOnMouseDragged(event -> {
            if(isPressed[0]){
                root.setCursor(Cursor.CLOSED_HAND);
                control.setLayoutX(event.getX()-x[0]);
                control.setLayoutY(event.getY()-y[0]);
            }
        });

        root.getChildren().addAll(background, control);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            background.setWidth(scene.getWidth());
            background.setHeight(scene.getHeight());

            red[0] = xControl[0]/control.getWidth();
            if(red[0]<0) red[0]=0;
            green[0] = 1-(yControl[0]/control.getHeight());
            if(green[0]<0) green[0]=0;

            if(control.getLayoutX()<0) blue[0]=0;
            else if (control.getLayoutX()>scene.getWidth()-control.getWidth()) blue[0]=1;
            else blue[0] = control.getLayoutX()/(scene.getWidth()-control.getWidth());

            color[0] = Color.color(red[0], green[0], blue[0]);
            background.setFill(color[0]);
            control.setFill(color[0]);
        }));

        timeline.setCycleCount(-1);
        timeline.play();

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
