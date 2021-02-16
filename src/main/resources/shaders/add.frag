// A simple add shader, will get a texture as input and add its value to the
// source texture. Alpha is applied as an average of the pixel color (black =
// total alpha, white = no alpha). Useful to blend textures from two
// different pipelines.

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform vec2 resolution;

uniform sampler2D u_texture1;

void main(void) {

    vec4 filterPass = texture(u_texture1, v_texCoords);
    vec4 source = texture(u_texture, v_texCoords);

    //source.a = (source.r + source.g + source.b) / 3.0;
    //filterPass.a = (filterPass.r + filterPass.g + filterPass.b) / 3.0;
    vec4 col = source + filterPass;
    
    gl_FragColor = vec4(col);
    //gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);

}
