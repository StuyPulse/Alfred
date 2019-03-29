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

@Description(dataTypes = {FieldPosition.class}, name = "Field Map")
@ParametrizedController("MapWidget.fxml")
public class MapWidget extends SimpleAnnotatedWidget {

    @FXML
    Pane mapPane;
    @FXML
    Canvas canvas;
    //TODO: FIND OUT WHY THIS LINE IS CAUSING PROBLEMS
//    GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public Pane getView() {
        Image fieldMap = new Image(getClass().getResourceAsStream("FieldMap.jpg"));
        //TODO: FIND OUT WHY THIS LINE WORKS
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(fieldMap,Constants.imgX, Constants.imgY);
//        gc.setFill(Color.RED);
//        gc.fillPolygon(new double[] {0, 0, 3}, new double[] {0, 2, 1}, 3);
        return mapPane;
    }
}
