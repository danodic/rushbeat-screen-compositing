varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;
uniform vec2 resolution;

uniform float intensity;

void main(void) {

	vec3 col = texture2D(u_texture, v_texCoords).rgb;
	vec3 invCol = 1.0-col;

	vec3 tCol = vec3(0.0);
	tCol.r = col.r + (invCol.r - col.r) * intensity;
	tCol.g = col.g + (invCol.g - col.g) * intensity;
	tCol.b = col.b + (invCol.b - col.b) * intensity;

	gl_FragColor = vec4(tCol, 1.0);

}
