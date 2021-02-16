package dev.danodic.rushbeat.rushbeat.compositor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import static com.badlogic.gdx.graphics.GL20.GL_TEXTURE0;
import static com.badlogic.gdx.graphics.GL20.GL_TEXTURE1;
import static com.badlogic.gdx.graphics.GL20.GL_TEXTURE5;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import dev.danodic.rushbeat.core.BatchFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author danilo
 */
public class Pipeline extends ArrayList<PipelineStage> {

    private final SpriteBatch sBatch;
    private final FrameBuffer fBuffer;
    private Integer destinationColor;
    private Integer sourceColor;
    
    
    public static Integer bindIndex = 1;

    public Pipeline(Integer virtualScreenWidth, Integer virtualScreenHeight) {
        destinationColor = GL20.GL_DST_COLOR;
        sourceColor = GL20.GL_ONE_MINUS_SRC_ALPHA;
        fBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        sBatch = BatchFactory.getSpriteBatch(virtualScreenWidth, virtualScreenHeight);        
    }

    public Texture render(Texture texture) {
        for (PipelineStage stage : this) {
            texture = stage.render(texture);
        }
        return texture;
    }

    public void setBlendFunction(Integer sourceColor, Integer destinationColor) {
        this.sourceColor = sourceColor;
        this.destinationColor = destinationColor;
    }

    public Integer getDestinationColor() {
        return destinationColor;
    }

    public void setDestinationColor(Integer destinationColor) {
        this.destinationColor = destinationColor;
    }

    public Integer getSourceColor() {
        return sourceColor;
    }

    public void setSourceColor(Integer sourceColor) {
        this.sourceColor = sourceColor;
    }

}
