package frc.plugin;

import edu.wpi.first.shuffleboard.api.data.types.NoneType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

@Description(dataTypes = {NoneType.class}, name = "Field Picture")
@ParametrizedController("PictureWidget.fxml")
public class PictureWidget extends SimpleAnnotatedWidget {
    @FXML
    ImageView img;
    @FXML
    Pane pane;

    @Override
    public Pane getView() {
        img = new ImageView(new Image(getClass().getResourceAsStream("FieldMap.png")));
        return pane;
    }
}
