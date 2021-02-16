package dev.danodic.rushbeat.rushbeat.compositor.stages;

import com.badlogic.gdx.graphics.Texture;
import dev.danodic.rushbeat.rushbeat.compositor.PipelineStage;
import java.util.HashMap;

/**
 *
 * @author danodic
 */
public class Add extends PipelineStage {

    private static final String VERTEX_SHADER_PATH = "shaders/simple.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/add.frag";

    private static final String TEXTURE_PARAMETER = "toAdd";

    public Add(Integer screenHeight, Integer screenWidth) {
        super(screenHeight, screenWidth, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH, new HashMap<>());
    }

    public void setTextureToAdd(Texture texture) {
        this.setShaderValue(TEXTURE_PARAMETER, texture);
    }

}
