package org.squidwrench.thor.barbaricplutonium;

import android.app.Activity;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BarbaricPlutonium extends Activity implements OnClickListener
{
  private static final float[] factor = { 0.5000f, 0.5625f, 0.6250f, 0.6667f,
	                                      0.7500f, 0.8333f, 0.9375f, 
	                                      1.0000f, 1.1250f, 1.2500f, 1.3333f, 
	                                      1.5000f, 1.6667f, 1.8750f, }; 
  private SoundEffects sfx;
  private GLSurfaceView surface;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
 	  setVolumeControlStream(AudioManager.STREAM_MUSIC);
    sfx = new SoundEffects(this);
    surface = new GLSurfaceView(this);
    surface.setRenderer(new FireRenderer());

    setContentView(surface);
  }

  protected void onPause() {
    super.onPause();
    surface.onPause();
  }
  
  protected void onResume() {
    super.onResume();
    surface.onResume();
  }
  public void onClick(View button) {
	  sfx.play_sound(button.getId());
  }
 }
