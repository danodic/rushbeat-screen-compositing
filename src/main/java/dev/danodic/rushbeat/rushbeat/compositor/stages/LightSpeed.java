package dev.danodic.rushbeat.rushbeat.compositor.stages;

import dev.danodic.rushbeat.rushbeat.compositor.PipelineStage;
import java.util.HashMap;

/**
 *
 * @author danodic
 */
public class LightSpeed extends PipelineStage {

    private static final String VERTEX_SHADER_PATH = "shaders/simple.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/lightspeed.frag";

    private static final String WIDTH_PARAMETER = "blurWidth";
    private static final String STROKE_SIZE_PARAMETER = "blurStroke";
    private static final String SAMPLES_PARAMETER = "samples";

    public LightSpeed(Integer screenHeight, Integer screenWidth) {
        super(screenHeight, screenWidth, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH, new HashMap<String, Object>() {
            {
                put(WIDTH_PARAMETER, 0f);
                put(STROKE_SIZE_PARAMETER, 0f);
                put(SAMPLES_PARAMETER, 50f);
            }
        });
    }

    public void setWidth(Float width) {
        this.setShaderValue(WIDTH_PARAMETER, width);
    }

    public void setStrokeSize(Float strokeSize) {
        this.setShaderValue(STROKE_SIZE_PARAMETER, strokeSize);
    }

    public void setSamples(Integer samples) {
        this.setShaderValue(SAMPLES_PARAMETER, samples);
    }

}
