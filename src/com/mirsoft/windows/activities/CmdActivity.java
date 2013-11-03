package com.mirsoft.windows.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.mirsoft.windows.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User: mirko @ PressMatrix GmbH
 * Date: 03.11.13 | Time: 13:27
 */
public class CmdActivity extends Activity implements View.OnKeyListener, TextView.OnEditorActionListener {

    private TextView mConsole;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cmd);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Ms-Dos");

        mConsole = (TextView) findViewById(R.id.cmd_console);
        mEdit = (EditText) findViewById(R.id.cmd);
        mEdit.setOnEditorActionListener(this);
//        writeADBLogs();
    }

    private void writeADBLogs(){
        Process process = null;
        try {
            String[] command = mEdit.getText().toString().replace("dir", "ls").split(" ");
            if (command.length>1) {
                process = new ProcessBuilder()
                        .command("/system/bin/" + command[0],command[1]
                        )
                        .redirectErrorStream(true)
                        .start();

            } else {
            process = new ProcessBuilder()
                    .command("/system/bin/" + command[0]
                           )
                    .redirectErrorStream(true)
                    .start();
            }

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()));

            mConsole.setText("");
            String oneLine;
            while ((oneLine= bufferedReader.readLine()) != null) {
                mConsole.setText(mConsole.getText().toString() + oneLine + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            process.destroy();
        }
//        try {
//            Process process = Runtime.getRuntime().exec("logcat -d");
//            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()));
//
//            String oneLine;
//            while ((oneLine= bufferedReader.readLine()) != null) {
//                mConsole.setText(mConsole.getText().toString() + oneLine + "\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE){
            Log.d("Windows3.1", "c: " + mEdit.getText());
            writeADBLogs();
            mEdit.setText("");
        }
        return false;
    }
}
