package frc.plugin;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

@Description(dataTypes = {FieldPosition.class}, name = "Field Map")
@ParametrizedController("MapWidget.fxml")
public final class MapWidget extends SimpleAnnotatedWidget {

    @FXML
    Pane mapPane;
    @FXML
    Canvas mapLayer;
    @FXML
    Canvas robotLayer;
    //TODO: FIND OUT WHY THIS LINE IS CAUSING PROBLEMS
//    GraphicsContext gc = canvas.getGraphicsContext2D();
//    private ObjectProperty<Image> img =
//        new SimpleObjectProperty<>(new Image(getClass().
//        getResource("2019-FieldMap.jpg").toExternalForm()));

    public void initMap(FieldPosition.StartingPosition pos) {
        GraphicsContext gc = robotLayer.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillPolygon(drawRobot(pos)[0], drawRobot(pos)[1], 3);
        gc.rotate(pos.angle);
    }

    @Override
    public Pane getView() {
        Image fieldMap = new Image(getClass().getResourceAsStream("2019-FieldMap.png"));
        //TODO: FIND OUT WHY THIS LINE WORKS
        GraphicsContext gc = mapLayer.getGraphicsContext2D();
        //gc.drawImage(img.get(),0, 0);
        gc.drawImage(fieldMap, 0, 0);
        gc.setFill(Color.RED);
        mapLayer.toBack();
        return mapPane;
    }

    private double[][] drawRobot(FieldPosition.StartingPosition pos) {
        return drawRobot(pos.x, pos.y);
    }

    private double[][] drawRobot(double x, double y) {
        double[][] val = new double[3][2];
        double size = Constants.ROBOT_SIZE;
        val[0][0] = x; val[0][1] = y;
        val[1][0] = x - size; val[1][1] = y + size / 2;
        val[2][0] = x - size; val[2][1] = y - size / 2;
        return val;
    }
}
