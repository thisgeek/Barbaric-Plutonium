package org.squidwrench.thor.barbaricplutonium;

import android.app.Activity;
import android.os.Bundle;

public class BarbaricPlutonium extends Activity
{
  private static final int GRID_SIZE = 16;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    TableLayout layout = new TableLayout(this);
    for(int i = 0; i < GRID_SIZE; i++) {
      TableRow row = new TableRow();
      for(int j = 0; j < GRID_SIZE; j++) {
        Button b = new Button();
        row.addView(b);
      }
      layout.addView(row);
    }
    super.onCreate(savedInstanceState);
    setContentView(layout);
  }
}
