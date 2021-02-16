package dev.danodic.rushbeat.rushbeat.compositor.stages;

import dev.danodic.rushbeat.rushbeat.compositor.PipelineStage;
import java.util.HashMap;

/**
 *
 * @author danodic
 */
public class MotionBlur extends PipelineStage {

    private static final String VERTEX_SHADER_PATH = "shaders/simple.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/motion_blur.frag";

    private static final String WIDTH_PARAMETER = "blurWidth";
    private static final String SAMPLES = "samples";

    public MotionBlur(Integer screenHeight, Integer screenWidth) {
        super(screenHeight, screenWidth, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH, new HashMap<String, Object>() {
            {
                put(WIDTH_PARAMETER, 0f);
                put(SAMPLES, 20f);
            }
        });
    }

    public void setWidth(Float width) {
        this.setShaderValue(WIDTH_PARAMETER, width);
    }

    public void setSamples(Integer size) {
        this.setShaderValue(SAMPLES, size);
    }

}
