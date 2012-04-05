package org.squidwrench.thor.barbaricplutonium;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

public class FireRenderer implements GLSurfaceView.Renderer {
  private static final int GRID_SIZE_X = 7;
  private static final int GRID_SIZE_Y = 10;
  private int[][] buffer;
  private FloatBuffer quadBuffer;
  private Random r;
  private static int[] palette = { 
                                  0xff000000, 0xff030000, 0xff060000, 0xff090000, 0xff0c0000, 0xff0f0000, 0xff120000, 0xff150000,
                                  0xff180000, 0xff1b0000, 0xff1e0000, 0xff210000, 0xff240000, 0xff270000, 0xff2a0000, 0xff2d0000,
                                  0xff300000, 0xff330000, 0xff360000, 0xff390000, 0xff3c0000, 0xff3f0000, 0xff420000, 0xff450000,
                                  0xff480000, 0xff4b0000, 0xff4e0000, 0xff510000, 0xff540000, 0xff570000, 0xff5a0000, 0xff5d0000,
                                  0xff600000, 0xff630000, 0xff660000, 0xff690000, 0xff6c0000, 0xff6f0000, 0xff720000, 0xff750000,
                                  0xff780000, 0xff7b0000, 0xff7e0000, 0xff810000, 0xff840000, 0xff870000, 0xff8a0000, 0xff8d0000,
                                  0xff900000, 0xff930000, 0xff960000, 0xff990000, 0xff9c0000, 0xff9f0000, 0xffa20000, 0xffa50000,
                                  0xffa80000, 0xffab0000, 0xffae0000, 0xffb10000, 0xffb40000, 0xffb70000, 0xffba0000, 0xffbd0000,
                                  0xffc00000, 0xffc30000, 0xffc60000, 0xffc90000, 0xffcc0000, 0xffcf0000, 0xffd20000, 0xffd50000,
                                  0xffd80000, 0xffdb0000, 0xffde0000, 0xffe10000, 0xffe40000, 0xffe70000, 0xffea0000, 0xffed0000,
                                  0xfff00000, 0xfff30000, 0xfff60000, 0xfff90000, 0xfffc0000, 0xffff0000, 0xffff0300, 0xffff0600,
                                  0xffff0900, 0xffff0c00, 0xffff0f00, 0xffff1200, 0xffff1500, 0xffff1800, 0xffff1b00, 0xffff1e00,
                                  0xffff2100, 0xffff2400, 0xffff2700, 0xffff2a00, 0xffff2d00, 0xffff3000, 0xffff3300, 0xffff3600,
                                  0xffff3900, 0xffff3c00, 0xffff3f00, 0xffff4200, 0xffff4500, 0xffff4800, 0xffff4b00, 0xffff4e00,
                                  0xffff5100, 0xffff5400, 0xffff5700, 0xffff5a00, 0xffff5d00, 0xffff6000, 0xffff6300, 0xffff6600,
                                  0xffff6900, 0xffff6c00, 0xffff6f00, 0xffff7200, 0xffff7500, 0xffff7800, 0xffff7b00, 0xffff7e00,
                                  0xffff8100, 0xffff8400, 0xffff8700, 0xffff8a00, 0xffff8d00, 0xffff9000, 0xffff9300, 0xffff9600,
                                  0xffff9900, 0xffff9c00, 0xffff9f00, 0xffffa200, 0xffffa500, 0xffffa800, 0xffffab00, 0xffffae00,
                                  0xffffb100, 0xffffb400, 0xffffb700, 0xffffba00, 0xffffbd00, 0xffffc000, 0xffffc300, 0xffffc600,
                                  0xffffc900, 0xffffcc00, 0xffffcf00, 0xffffd200, 0xffffd500, 0xffffd800, 0xffffdb00, 0xffffde00,
                                  0xffffe100, 0xffffe400, 0xffffe700, 0xffffea00, 0xffffed00, 0xfffff000, 0xfffff300, 0xfffff600,
                                  0xfffff900, 0xfffffc00, 0xffffff00, 0xffffff03, 0xffffff06, 0xffffff09, 0xffffff0c, 0xffffff0f,
                                  0xffffff12, 0xffffff15, 0xffffff18, 0xffffff1b, 0xffffff1e, 0xffffff21, 0xffffff24, 0xffffff27,
                                  0xffffff2a, 0xffffff2d, 0xffffff30, 0xffffff33, 0xffffff36, 0xffffff39, 0xffffff3c, 0xffffff3f,
                                  0xffffff42, 0xffffff45, 0xffffff48, 0xffffff4b, 0xffffff4e, 0xffffff51, 0xffffff54, 0xffffff57,
                                  0xffffff5a, 0xffffff5d, 0xffffff60, 0xffffff63, 0xffffff66, 0xffffff69, 0xffffff6c, 0xffffff6f,
                                  0xffffff72, 0xffffff75, 0xffffff78, 0xffffff7b, 0xffffff7e, 0xffffff81, 0xffffff84, 0xffffff87,
                                  0xffffff8a, 0xffffff8d, 0xffffff90, 0xffffff93, 0xffffff96, 0xffffff99, 0xffffff9c, 0xffffff9f,
                                  0xffffffa2, 0xffffffa5, 0xffffffa8, 0xffffffab, 0xffffffae, 0xffffffb1, 0xffffffb4, 0xffffffb7,
                                  0xffffffba, 0xffffffbd, 0xffffffc0, 0xffffffc3, 0xffffffc6, 0xffffffc9, 0xffffffcc, 0xffffffcf,
                                  0xffffffd2, 0xffffffd5, 0xffffffd8, 0xffffffdb, 0xffffffde, 0xffffffe1, 0xffffffe4, 0xffffffe7,
                                  0xffffffea, 0xffffffed, 0xfffffff0, 0xfffffff3, 0xfffffff6, 0xfffffff9, 0xfffffffc, 0xffffffff,
                               };

  private void initShapes() {
    float squares[] = { 
                         -0.5f, -0.25f, 0,
                         0.5f, -0.25f, 0,
                         0.0f,  0.559016994f, 0

    };
    /*-1f, -1f, 0f,
                        -1f,  0f, 0f,
                         0f,  0f, 0f,
                         0f, -1f, 0f,
                        -1f,  0f, 0f,
                        -1f,  1f, 0f,
                         0f,  1f, 0f,
                         0f,  0f, 0f,
                         0f, -1f, 0f,
                         0f,  0f, 0f,
                         1f,  0f, 0f,
                         1f, -1f, 0f,
                         0f,  0f, 0f,
                         0f,  1f, 0f,
                         1f,  1f, 0f,
                         1f,  0f, 0f,*/
    ByteBuffer vbb = ByteBuffer.allocateDirect(
        squares.length * 4);
    vbb.order(ByteOrder.nativeOrder());
    quadBuffer = vbb.asFloatBuffer();
    quadBuffer.put(squares);
    quadBuffer.position(0);
  }
  
  public void onSurfaceCreated(GL10 gl, EGLConfig egl) {
    buffer = new int[GRID_SIZE_Y + 2][GRID_SIZE_X];
    r = new Random();
    initShapes();
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
  }
  
  public void onSurfaceChanged(GL10 gl, int w, int h) {
    gl.glViewport(0, 0, w, h);
  }
  
  public void onDrawFrame(GL10 gl) {
    int index = 0;
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    for(int i = GRID_SIZE_Y - 1; i >= 0; i--) {
      for(int j = 0; j < GRID_SIZE_X; j++, index++) {
        int i1 = (i == 0) ? 0 : i - 1;
        int i3 = (i == GRID_SIZE_Y - 1) ? i : i + 1;
        int j1 = (j == 0) ? 0 : j - 1;
        int j3 = (j == GRID_SIZE_X - 1) ? j : j + 1;
        buffer[i][j] = (buffer[i1][j1] +
                        buffer[i1][j]  +
                        buffer[i1][j3] +
                        buffer[i][j1]  +
                        buffer[i][j]   +
                        buffer[i][j3]  +
                        buffer[i3][j1] +
                        buffer[i3][j]  +
                        buffer[i3][j3] ) / 9;
        int color = palette[buffer[i][j]];
        int r = (color & 0x00FF0000) <<  0;
        int g = (color & 0x0000FF00) <<  8;
        int b = (color & 0x000000FF) << 16;
        gl.glColor4x(r / 255, g / 255, b / 255, 1);
        if(index ==0) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, quadBuffer);
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 4, 3);
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 8, 3);
        //gl.glDrawArrays(GL10.GL_TRIANGLES, 12, 3);
        }
      }
    }
    
    for(int i = GRID_SIZE_Y - 1; i < GRID_SIZE_Y + 2; i++) {
      for(int j = 0; j < GRID_SIZE_X; j++) {
      buffer[i][j] = r.nextInt(255);
      }
    }
    
  }
}