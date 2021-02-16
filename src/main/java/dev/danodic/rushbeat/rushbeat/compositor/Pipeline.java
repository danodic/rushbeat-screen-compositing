package dev.danodic.rushbeat.rushbeat.compositor;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;

/**
 * A list of pipeline stages.
 * @author danilo
 */
public class Pipeline extends ArrayList<PipelineStage> {

    public Texture render(Texture texture) {
        for (PipelineStage stage : this) {
            texture = stage.render(texture);
        }
        return texture;
    }

}
