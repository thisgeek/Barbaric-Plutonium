package org.squidwrench.thor.barbaricplutonium;

import java.util.HashMap;

import android.content.Context;
import android.media.SoundPool;
import android.media.AudioManager;

/**
 * 
 */

/**
 * @author thor
 *
 */
public class SoundEffects {
  private SoundPool sound_pool;
  private HashMap<Integer, Integer> sound_pool_map;
  private HashMap<Integer, Float>   sound_pool_pitch_map;
  private HashMap<Integer, Float>   sound_pool_panning_map;
  private Context appl_context;
  //private AudioManager audio_manager;
  
  public SoundEffects(Context ctx) {
	  appl_context = ctx;
	  sound_pool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
	  sound_pool_map = new HashMap<Integer, Integer>();
	  sound_pool_pitch_map = new HashMap<Integer, Float>();
	  sound_pool_panning_map = new HashMap<Integer, Float>();
	  //audio_manager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);
  }
  
  public void add_sound(int index, int sound_res, float pitch, float panning) {
	  sound_pool_map.put(index, sound_pool.load(appl_context, sound_res, 1));
	  sound_pool_pitch_map.put(index, pitch);
	  sound_pool_panning_map.put(index, panning);
  }
  
  public void play_sound(int index) {
	  //float stream_volume = audio_manager.getStreamVolume(AudioManager.STREAM_MUSIC);
	  //stream_volume /= audio_manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	  //sound_pool.play(sound_pool_map.get(index), stream_volume, stream_volume, 1, 0, sound_pool_factor_map.get(index));
	  float panning = sound_pool_panning_map.get(index);
	  sound_pool.play(sound_pool_map.get(index), 1-panning, panning, 1, 0, sound_pool_pitch_map.get(index));
  }
}
