// Copied from https://www.shadertoy.com/view/XsfSDs
varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform vec2 resolution;

uniform float blurWidth;
uniform float blurStroke;
uniform int samples;

void main(void) {

	vec2 center = vec2(0.5, 0.5);
	float blurStart = 1.0;

	vec2 uv = v_texCoords - center;
	float precompute = blurWidth * (1.0 * blurStroke / float(samples - 1));

	vec4 color = vec4(0.0);
	for (int i = 0; i < samples; i++) {
		float scale = blurStart + (float(i) * precompute);
		color += texture2D(u_texture, uv * scale + center);
	}

	//color /= float(samples);

        //color = texture2D(u_texture, v_texCoords);

	gl_FragColor = color;

}

