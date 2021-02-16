package dev.danodic.rushbeat.rushbeat.compositor.stages;

import dev.danodic.rushbeat.rushbeat.compositor.PipelineStage;
import java.util.HashMap;

/**
 *
 * @author danodic
 */
public class Invert extends PipelineStage {

    private static final String VERTEX_SHADER_PATH = "shaders/simple.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/invert.frag";

    private static final String INTENSITY_PARAMETER = "intensity";

    public Invert(Integer screenHeight, Integer screenWidth) {
        super(screenHeight, screenWidth, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH, new HashMap<String, Object>() {
            {
                put(INTENSITY_PARAMETER, 0f);
            }
        });
    }

    public void setIntensity(Float intensity) {
        this.setShaderValue(INTENSITY_PARAMETER, intensity);
    }

}
