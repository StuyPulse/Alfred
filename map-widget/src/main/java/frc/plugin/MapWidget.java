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

//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;

@Description(dataTypes = {FieldPosition.class}, name = "Field Map")
@ParametrizedController("MapWidget.fxml")
public final class MapWidget extends SimpleAnnotatedWidget {

    @FXML
    Pane mapPane;
    @FXML
    Canvas mapLayer;

    FieldPosition fieldPosition;
    //TODO: Implement the camera switcher in the field map
//    @FXML
//    Canvas cameraLayer;
    //TODO: FIND OUT WHY THIS LINE IS CAUSING PROBLEMS
//    GraphicsContext gc = canvas.getGraphicsContext2D();
//    private ObjectProperty<Image> img =
//        new SimpleObjectProperty<>(new Image(getClass().
//        getResource("2019-FieldMap.jpg").toExternalForm()));

    public void update(FieldPosition pos, GraphicsContext gc) {
        drawRobot(gc, pos.getX(), pos.getY(), pos.getAngle());
//        gc.fillPolygon(
//            drawRobot(pos.getX(), pos.getY())[0],
//            drawRobot(pos.getX(), pos.getY())[1],
//            3
//        );
//        gc.rotate(pos.getAngle());
    }

    @Override
    public Pane getView() {
        Image fieldMap = new Image(getClass().getResourceAsStream("2019-FieldMap.png"));
        //TODO: FIND OUT WHY THIS LINE WORKS
        GraphicsContext gc = mapLayer.getGraphicsContext2D();
        //gc.drawImage(img.get(),0, 0);
        gc.drawImage(fieldMap, 0, 0);
        gc.setFill(Color.RED);
        //gc.fillPolygon(yoop, woop, 3);
//        gc.fillPolygon(new double[] {0, 0, 2}, new double[] {0, 3, 1}, 3);
//        update(fieldPosition, gc);
        drawRobot(gc, 100, 100, 0);
//        mapLayer.toBack();
//        update(new FieldPosition(FieldPosition.StartingPosition.LEFT_CS, 0));
        return mapPane;
    }

    private double[][] drawRobot(GraphicsContext gc, double x, double y, double angle) {
        double[][] val = new double[2][4];
        double length = Constants.ROBOT_LENGTH;
        double width = Constants.ROBOT_WIDTH;
        val[0][0] = x;                  val[1][0] = y;
        val[0][1] = x - length;         val[1][1] = y + width / 2;
        val[0][2] = x - length * 3 / 4; val[1][2] = y;
        val[0][3] = x - length;         val[1][3] = y - width / 2;

        gc.fillPolygon(val[0], val[1], 4);
        gc.rotate(angle);
        return val;
    }
}

