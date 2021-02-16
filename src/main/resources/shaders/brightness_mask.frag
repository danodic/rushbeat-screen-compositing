// Generates a brightness mask, where all pixels above a certain treshold will
// be output normally and everthing below is just black. This is the firt step
// for bloom effects.

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform vec2 resolution;

uniform float brightness;
uniform float contrast;

void main(void) {
    vec4 col = vec4(0);
    vec4 pixel = texture(u_texture, v_texCoords);
    if(pixel.r >= contrast && pixel.g >= contrast && pixel.b >= contrast) {
        col.rgba += vec4(pixel.rgb * vec3(brightness), 1.0);
    }
    gl_FragColor = vec4(col);
}
