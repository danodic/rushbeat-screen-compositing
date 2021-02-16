// https://www.shadertoy.com/view/XtjSRG
varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform vec2 resolution;

uniform float chromaIntensity;

const float freqInterval = 0.1;
const float freqStart = -1.0;
const float sampleSize = 0.02;

void main(void) {

    float intensity = 0.0;
    for(float s = 0.0; s < freqInterval; s += freqInterval * sampleSize) {
        intensity += chromaIntensity;
    }

    intensity = abs(intensity);
    intensity = pow((intensity*sampleSize),3.0)*4.0;

    vec2 rOffset = vec2(-0.02,0)*intensity;
    vec2 gOffset = vec2(0.0,0)*intensity;
    vec2 bOffset = vec2(0.02,0)*intensity;
    
    vec4 rValue = texture(u_texture, v_texCoords - rOffset);
    vec4 gValue = texture(u_texture, v_texCoords - gOffset);
    vec4 bValue = texture(u_texture, v_texCoords -  bOffset);

    gl_FragColor = vec4(rValue.r, gValue.g, bValue.b, 1.0);

}
