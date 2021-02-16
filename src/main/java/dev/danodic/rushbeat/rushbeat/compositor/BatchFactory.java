package dev.danodic.rushbeat.rushbeat.compositor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Takes care of instantiating the sprite batches with the right settings.
 *
 * @author danodic
 */
public class BatchFactory {

    public static SpriteBatch getSpriteBatch(Integer resX, Integer resY) {
        SpriteBatch batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, resX, resY);
        batch.enableBlending();
        return batch;
    }

}
