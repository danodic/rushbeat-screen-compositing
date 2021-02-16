package dev.danodic.rushbeat.rushbeat.compositor.stages;

import dev.danodic.rushbeat.rushbeat.compositor.PipelineStage;
import java.util.HashMap;

/**
 *
 * @author danodic
 */
public class HorizontalBlur extends PipelineStage {

    private static final String VERTEX_SHADER_PATH = "shaders/simple.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/horizontal_blur.frag";

    private static final String SAMPLES_PARAMETER = "samples";
    private static final String SIZE_PARAMETER = "size";
    private static final String BRIGHTNESS_PARAMETER = "brightness";

    public HorizontalBlur(Integer screenHeight, Integer screenWidth) {
        super(screenHeight, screenWidth, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH, new HashMap<String, Object>() {
            {
                put(SAMPLES_PARAMETER, 0f);
                put(SIZE_PARAMETER, 0f);
                put(BRIGHTNESS_PARAMETER, 0f);
            }
        });
    }

    public void setSamples(Float value) {
        this.setShaderValue(SAMPLES_PARAMETER, value);
    }

    public void setSize(Float value) {
        this.setShaderValue(SIZE_PARAMETER, value);
    }

    public void setBrightness(Float value) {
        this.setShaderValue(BRIGHTNESS_PARAMETER, value);
    }

}
