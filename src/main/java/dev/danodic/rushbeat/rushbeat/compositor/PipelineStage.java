package dev.danodic.rushbeat.rushbeat.compositor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A single pipeline stage. It is supposed to be added to an instance of
 * Pipeline.
 * @author danodic
 */
public class PipelineStage {

    private final ShaderProgram shaderProgram;
    private final SpriteBatch sBatch;
    private final FrameBuffer fBuffer;
    private HashMap<String, Object> values;
    private Boolean unbind = Boolean.FALSE;

    public PipelineStage(Integer virtualScreenWidth, Integer virtualScreenHeight, String vertexShaderPath, String fragmentShaderPath, Map<String, Object> variables) {
        values = new HashMap<>();
        fBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        sBatch = BatchFactory.getSpriteBatch(virtualScreenWidth, virtualScreenHeight);

        String vertexShader, fragmentShader;

        try {
            vertexShader = Resources.toString(Resources.getResource(vertexShaderPath), Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Could not read shader file.: " + vertexShaderPath);
        }

        try {
            fragmentShader = Resources.toString(Resources.getResource(fragmentShaderPath), Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Could not read shader file.: " + fragmentShaderPath);
        }

        shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
        initializeShader(variables);
    }

    public final void initializeShader(Map<String, Object> variables) {
        variables.entrySet().forEach(var -> {
            setShaderValue(var.getKey(), var.getValue());
        });
    }

    public Texture render(Texture sourceTexture) {
        fBuffer.begin();
        sBatch.begin();
        clear();
        sBatch.setShader(shaderProgram);
        values.forEach((k, v) -> applyShaderValue(k, v));
        sBatch.draw(sourceTexture, 0f, 0f);
        sBatch.end();
        fBuffer.end();
        if (unbind) {
            Gdx.graphics.getGL20().glBindTexture(GL20.GL_TEXTURE0, 0);
        }
        return fBuffer.getColorBufferTexture();
    }

    private void clear() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void setShaderValue(String name, Object var) {
        values.put(name, var);
    }

    public void applyShaderValue(String name, Object var) {
        if (var instanceof Integer) {
            shaderProgram.setUniformi(name, Integer.class.cast(var));
        } else if (var instanceof Float) {
            shaderProgram.setUniformf(name, Float.class.cast(var));
        } else if (var instanceof Texture) {
            Texture.class.cast(var).bind(0);
            shaderProgram.setUniformi(name, 0);
            unbind = true;
        } else {
            throw new RuntimeException(String.format("Unsupported variable type '%s' for shader.", var.getClass().getName()));
        }
    }

}
