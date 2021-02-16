// Blurs the image linerarly in the x axis.

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform vec2 resolution;

uniform float samples;
uniform float size;
uniform float brightness;

void main(void) {

    vec2 dispersion = vec2(size * 0.001);

    float blurStep = 1.0 / (samples / 2.0);
    
    vec4 col = vec4(0.0);
    for(float posY = -samples; posY<samples; posY++) {
        vec4 pixel = texture(u_texture, v_texCoords + vec2(0.0, posY) * dispersion);
        float alpha = (pixel.r + pixel.g + pixel.b) / 3.0;
        col.rgb += pixel.rgb * vec3(blurStep * brightness);
        col.a += alpha;
    }

    col.rgba = col.rgba / vec4(samples);
    gl_FragColor = vec4(col);

}
