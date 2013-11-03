package com.mirsoft.windows;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.mirsoft.windows.activities.Bluescreen;
import com.mirsoft.windows.activities.CmdActivity;
import com.mirsoft.windows.activities.EditorActivity;
import com.mirsoft.windows.activities.MineSweeperActivity;

public class StartScreen extends Activity implements Animation.AnimationListener {
    private ImageView mStartScreen;
    private MediaPlayer mMediaPlayerStart;
    private PopupWindow mPopupWindow;
    private MediaPlayer mMediaPlayerError;

    private TextView mOpenWindow;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        mMediaPlayerStart = MediaPlayer.create(this, R.raw.win31);
        mMediaPlayerError = MediaPlayer.create(this, R.raw.error);

        initStartMenu();

        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade);
        fadeOut.setAnimationListener(this);
        mStartScreen = (ImageView) findViewById(R.id.startscreen);
        mStartScreen.startAnimation(fadeOut);
        mStartScreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMediaPlayerStart.start();
            }
        }, 500);
    }

    private void initStartMenu(){
        mOpenWindow = (TextView) findViewById(R.id.open_window);

        final View popup = LayoutInflater.from(this).inflate(R.layout.popup_menu, null);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mPopupWindow = new PopupWindow(popup, (int)(210 * metrics.density), (int)(400 * metrics.density));
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources()));
        mPopupWindow.setOutsideTouchable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOpenWindow.setVisibility(View.GONE);
    }

    public void openStartMenu(View view){
        mPopupWindow.showAsDropDown(view);
    }

    public void onClickEditor(View view){
        startActivity(new Intent(this, EditorActivity.class));
        mOpenWindow.setVisibility(View.VISIBLE);
        mOpenWindow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_textedit), null, null, null);
        mOpenWindow.setText("Editor");
    }

    public void onClickMsDos(View view){
        startActivity(new Intent(this, CmdActivity.class));
        mPopupWindow.dismiss();
        mOpenWindow.setVisibility(View.VISIBLE);
        mOpenWindow.setText("MS-DOS");
        mOpenWindow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_msdos), null, null, null);
    }

    public void onClickIExplorer(View view){
        startActivity(new Intent(this, Bluescreen.class));
        mMediaPlayerError.start();
    }

    public void onClickMinesweeper(View view){
        startActivity(new Intent(this, MineSweeperActivity.class));
        mPopupWindow.dismiss();
        mOpenWindow.setVisibility(View.VISIBLE);
        mOpenWindow.setText("Minesweeper");
        mOpenWindow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_minesweeper), null, null, null);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mStartScreen.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
