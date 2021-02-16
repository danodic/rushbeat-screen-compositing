

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import dev.danodic.rushbeat.core.BatchFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * The screen compositor that takes care of applying post-processing and
 * downscaling the screen.
 *
 * @author danodic
 */
public class ScreenPipeline {

    private final FrameBuffer screenBuffer;
    private final FrameBuffer blendBuffer;

    private final SpriteBatch finalBatch;
    private final SpriteBatch blendBatch;

    private final List<Pipeline> pipelines;

    private Texture bufferTexture;
    private Sprite finalSprite;

    private Integer virtualScreenResX;
    private Integer virtualScreenResY;

    public ScreenPipeline(Integer virtualScreenResX, Integer virtualScreenResY) {
        this.virtualScreenResX = virtualScreenResX;
        this.virtualScreenResY = virtualScreenResY;

        pipelines = new ArrayList<>();
        finalBatch = BatchFactory.getSpriteBatch(virtualScreenResX, virtualScreenResY);
        blendBatch = BatchFactory.getSpriteBatch(virtualScreenResX, virtualScreenResY);
        screenBuffer = new FrameBuffer(Format.RGBA8888, virtualScreenResX, virtualScreenResY, false);
        blendBuffer = new FrameBuffer(Format.RGBA8888, virtualScreenResX, virtualScreenResY, false);
    }

    public Pipeline addPipeline() {
        Pipeline newPipeline;
        newPipeline = new Pipeline(virtualScreenResX, virtualScreenResY);
        pipelines.add(newPipeline);
        return newPipeline;
    }

    public List<Pipeline> getPipelines() {
        return pipelines;
    }

    public void begin() {
        screenBuffer.begin();
    }

    public void end() {
        screenBuffer.end();
    }

    Texture blendTexture;
    List<Texture> textures = new ArrayList<>();

    public void render() {
        bufferTexture = screenBuffer.getColorBufferTexture();

        textures.clear();

        pipelines.forEach(pipeline -> {
            textures.add(pipeline.render(bufferTexture));
        });

        blendBuffer.begin();
        blendBatch.begin();
        clearBuffer();
        blendBatch.enableBlending();
        blendBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        textures.forEach(texture -> {
            blendBatch.draw(texture, 0, 0);
        });
        blendBatch.end();
        blendBuffer.end();
        
        blendBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        finalSprite = new Sprite(blendBuffer.getColorBufferTexture());
        finalSprite.flip(false, true);

        finalBatch.begin();
        finalSprite.draw(finalBatch);
        finalBatch.end();

    }

    private void clearBuffer() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

}
