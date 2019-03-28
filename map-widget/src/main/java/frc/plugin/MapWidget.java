package frc.plugin;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

@Description(dataTypes = {FieldPosition.class}, name = "Field Map")
@ParametrizedController("MapWidget.fxml")
public class MapWidget extends SimpleAnnotatedWidget {

    @FXML
    Pane mapPane;
    @FXML
    Canvas canvas;
    GraphicsContext gc = canvas.getGraphicsContext2D();

    public void updateWidget(int x, int y) {
        gc.translate(x, y);
    }

    @Override
    public Pane getView() {
        //TODO: ADD PROPERTIES FILE TO MAKE THIS WORK
        Image fieldMap = new Image(MapWidget.class.getResourceAsStream("FieldMap.jpg/"));
        gc.drawImage(fieldMap,Constants.imgX, Constants.imgY);
        gc.setFill(Color.RED);
        gc.fillPolygon(new double[] {0, 0, 3}, new double[] {0, 2, 1}, 3);
        return mapPane;
    }
}
