package frc.plugin;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

@Description(dataTypes = {FieldPosition.class}, name = "Field Map")
@ParametrizedController("MapWidget.fxml")
public class MapWidget extends SimpleAnnotatedWidget {

    @FXML
    Pane mPane;
    @FXML
    Pane mapPane;
    @FXML
    Canvas canvas;

    @Override
    public Pane getView() {
        Image fieldMap = new Image("file:///");
        GraphicsContext gc = new Canvas().getGraphicsContext2D();
        gc.drawImage(fieldMap,400, 600);
        return mapPane;
    }

}
