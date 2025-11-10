package com.acntem.improveuiapp.presentation.screen.opengles

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SixStarRingsGL( modifier: Modifier = Modifier, context: Context) {
    AndroidView(factory = {
        GLSurfaceView(it).apply {
            setEGLContextClientVersion(2)
            setRenderer(StarRenderer())
            renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        }
    }, modifier = modifier.fillMaxSize())
}

@Preview
@Composable
fun SixStarRingsGLPreview(
) {
    val context = LocalContext.current
    SixStarRingsGL(context = context)
}

class StarRenderer : GLSurfaceView.Renderer {

    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val mvpMatrix = FloatArray(16)
    private val rotationMatrix = FloatArray(16)

    private var programHandle = 0
    private var angle = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(1f, 1f, 1f, 1f)
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        val vertexShaderCode = """
            uniform mat4 uMVPMatrix;
            attribute vec4 vPosition;
            void main() {
                gl_Position = uMVPMatrix * vPosition;
            }
        """

        val fragmentShaderCode = """
            precision mediump float;
            uniform vec4 vColor;
            void main() {
                gl_FragColor = vColor;
            }
        """

        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)

        programHandle = GLES20.glCreateProgram()
        GLES20.glAttachShader(programHandle, vertexShader)
        GLES20.glAttachShader(programHandle, fragmentShader)
        GLES20.glLinkProgram(programHandle)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val ratio: Float = width.toFloat() / height
        if (ratio >= 1f) {
            Matrix.orthoM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, -1f, 1f)
        } else {
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -1f / ratio, 1f / ratio, -1f, 1f)
        }
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1f, 0f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        GLES20.glUseProgram(programHandle)

        val positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition")
        val colorHandle = GLES20.glGetUniformLocation(programHandle, "vColor")
        val mvpMatrixHandle = GLES20.glGetUniformLocation(programHandle, "uMVPMatrix")

        val colors = arrayOf(
            floatArrayOf(1f, 0f, 0f, 0.5f),
            floatArrayOf(0f, 1f, 0f, 0.5f),
            floatArrayOf(0f, 0f, 1f, 0.5f),
            floatArrayOf(1f, 1f, 0f, 0.5f),
            floatArrayOf(1f, 0f, 1f, 0.5f),
            floatArrayOf(0f, 1f, 1f, 0.5f)
        )

        val timeRotation = (System.currentTimeMillis() % 4000L) / 4000f * 360f
        Matrix.setRotateM(rotationMatrix, 0, timeRotation, 0f, 0f, 1f)
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, rotationMatrix, 0)

        GLES20.glEnableVertexAttribArray(positionHandle)

        for (i in 0 until 6) {
            val color = colors[i % colors.size]
            GLES20.glUniform4fv(colorHandle, 1, color, 0)

            val radiusOuter = 0.2f + 0.1f * i
            val radiusInner = radiusOuter * 0.5f
            val points = 5

            val vertices = mutableListOf<Float>()
            vertices.add(0f)
            vertices.add(0f)

            for (j in 0..points * 2) {
                val angleDeg = 360f / (points * 2) * j
                val rad = Math.toRadians(angleDeg.toDouble())
                val r = if (j % 2 == 0) radiusOuter else radiusInner
                vertices.add((r * cos(rad)).toFloat())
                vertices.add((r * sin(rad)).toFloat())
            }

            val vertexBuffer: FloatBuffer = ByteBuffer
                .allocateDirect(vertices.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices.toFloatArray())
            vertexBuffer.position(0)

            GLES20.glVertexAttribPointer(
                positionHandle,
                2,
                GLES20.GL_FLOAT,
                false,
                0,
                vertexBuffer
            )

            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertices.size / 2)
        }

        GLES20.glDisableVertexAttribArray(positionHandle)
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }
}