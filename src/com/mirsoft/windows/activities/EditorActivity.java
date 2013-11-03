package com.mirsoft.windows.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.mirsoft.windows.R;

/**
 * User: mirko @ PressMatrix GmbH
 * Date: 03.11.13 | Time: 11:04
 */
public class EditorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Editor");
    }
}
