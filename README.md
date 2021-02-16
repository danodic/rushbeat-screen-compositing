# Rushbeat Screen Compositing
The Rushbeat Screen Compositing is a simpl library for adding post-processing effects to a framebuffer using LibGDX and GLSL shaders. It has been made to be as simple as possible, so that you can draw anything you want in between `pipeline.begin()` and `pipeline.end()` and see the post-processing pipelines applied after that.
This library is focused on applying **fragment shaders**, and ignores the fact the vertex shaders are a thing.

## Current state
I use this library in my personal project called Rushbeat, so as of now improvements are done as I implement features in the game. This is usable but very basic at the moment. The GLSL shaders included in it right now are quite basic and I have done no optimizations in the library as of now. Use at your own risk, you are welcome to contribute with new (or better) shaders. Also, the `pom.xml` has to be reworked so it picks up whatever LibGDX library version is being used in the project it has been imported into.

This library is the output of a couple weeks learning how frame buffers work in OpenGL and LibxGDX and is not a professional output. Still, may be useful for other people.

## Installing
As of now, the library has to be downloaded and installed in your local system using Maven. To do so, clone the main branch of this library and run `maven install`, you should be good to go after that.

## Including in another project
Add the following to your `pom.xml`:
```
<dependency>
    <groupId>dev.danodic.rushbeat</groupId>
    <artifactId>rushbeat-compositing</artifactId>
    <version>${version}</version>
</dependency>
```

## How it works
Post-processing effects are applied in the final result of the rendering pass of a frame. It picks up whatever has been drawn in the screen and post-processes it in order to apply different effects in the rendered image.

You can find an example here: [here](https://github.com/danodic/rushbeat-screen-compositing-example)

Some effects can be achieved in a single shader pass (like chromatic aberration) and others may require multiple shader passes (bloom requires a contrast adjustment shader and a gaussian blur shader). Due to that, the library offers the possibility of having multiple passes in a single processing pipeline. Also, it allows for adding multiple processing pipelines to be applied to the original output and then blends each pipeline output with each other using additive rendering.

In this library, we have three main classes:

- `PipelineStage` - Represents a shader pass. It loads a fragment shader and allows for parameters to be fed into a ShaderProgram instance. In this library, each different effect is a class that inherits from PipelineStage and exposes its own arguments as getters and setters, as well as it initializes the ShaderProgram with the right fragment shader. Creating a new pipeline stage for an specific shader pass is as simple as just instancing one of those classes, like `PipelineStage motionBlur = new MotionBlur()`.
- `Pipeline` - A pipeline carries many instances of `PipelineStage`. A pipeline is provided by the `ScreenPipeline` by using `firstPass = screenPipeline.addPipeline()`. The shader passes are applied in the order the pipeline stages have been added to the pipeline using `firstPass.add(motionBlur)`. You can add many pipelines to a `ScreenPipeline` and they will be rendered and blended using additive rendering.
- `ScreenPipeline` - The main pipeline class, this is what you want to use in your render loop. You can instantiate the `ScreenPipeline` directly as follows: `ScreenPipeline screenPipeline = new ScreenPipeline(1920, 1080);`. The resolution that is passed to the constructor must be the resolution of the frame buffer you are rendring to before any downscale.

You can find an example below:
```
public class Example implements ApplicationListener {

    Texture texture;
    SpriteBatch batch;
    ScreenPipeline screenPipeline;
    ChromaAberration chroma;
    Float intensity;

    @Override
    public void create() {
        batch = new SpriteBatch();
        intensity = 0f;
        texture = new Texture("assets/sample.png");

        // Create the screen pipeline
        screenPipeline = new ScreenPipeline(1600, 900);

        // Create a chroma aberration pass
        chroma = new ChromaAberration(1600, 900);

        // Create the pipeline pass and add stages
        Pipeline firstPass = screenPipeline.addPipeline();
        firstPass.add(chroma);
    }

    @Override
    public void render() {
        // Updates pipeline stage parameters
        update();
	
	// Clears the main drawing buffer
        clear();
        
        // Starts drawing inside the post-processing buffer
        screenPipeline.begin();
        
        // Just a regular texture draw
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
        
        // Ends drawing inside the post-processing buffer and
        // renders the final result to the scrren.
        screenPipeline.end();
        screenPipeline.render();
    }

    public void clear() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    
    public void update() {
        intensity += 0.01f;
        if(intensity>=1f) {
            intensity = 0.0f;
        }
	
	// Updates the input value of the shader
        chroma.setIntensity(intensity);
    }

    @Override
    public void resize(int arg0, int arg1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}
```
