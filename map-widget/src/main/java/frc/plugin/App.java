package frc.plugin;

import edu.wpi.first.shuffleboard.api.plugin.Plugin;

import com.google.common.collect.ImmutableList;

import java.util.List;

import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;

@Description(group = "StuyPulse 694", name = "Test", version="0.0.0", summary="Does absolutely nothing")

public class App extends Plugin {
    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(WidgetType.forAnnotatedWidget(MapWidget.class),
                                WidgetType.forAnnotatedWidget(PictureWidget.class));
    }
}
