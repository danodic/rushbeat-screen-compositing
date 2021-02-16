package dev.danodic.rushbeat.rushbeat.compositor.stages;

import dev.danodic.rushbeat.rushbeat.compositor.PipelineStage;
import java.util.HashMap;

/**
 *
 * @author danodic
 */
public class BrightnessMask extends PipelineStage {

    private static final String VERTEX_SHADER_PATH = "shaders/simple.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/brightness_mask.frag";

    private static final String BRIGHTNESS_PARAMETER = "brightness";
    private static final String CONTRAST_PARAMETER = "contrast";

    public BrightnessMask(Integer screenHeight, Integer screenWidth) {
        super(screenHeight, screenWidth, VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH, new HashMap<String, Object>() {
            {
                put(BRIGHTNESS_PARAMETER, 0f);
                put(CONTRAST_PARAMETER, 0f);
            }
        });

    }

    public void setBrightness(Float intensity) {
        this.setShaderValue(BRIGHTNESS_PARAMETER, intensity);
    }

    public void setContrast(Float intensity) {
        this.setShaderValue(CONTRAST_PARAMETER, intensity);
    }
}
