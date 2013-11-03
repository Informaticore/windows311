package com.mirsoft.windows.activities;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mirsoft.windows.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: mirko @ PressMatrix GmbH
 * Date: 03.11.13 | Time: 11:04
 */
public class MineSweeperActivity extends Activity {

    private GridView mGridView;
    private MineAdapter mAdapter;
    private MediaPlayer mMediaPlayerError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper);

        mMediaPlayerError = MediaPlayer.create(this, R.raw.error);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Minesweeper");

        mGridView = (GridView) findViewById(R.id.mine_grid);
        mAdapter = new MineAdapter(this);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mAdapter.setGameOver(position);
                mMediaPlayerError.start();
            }
        });

    }

    public class MineAdapter extends BaseAdapter{

        private final Context mContext;
        private ArrayList<Integer> mMines = new ArrayList<Integer>();
        private Random mRand;
        private boolean gameOver;

        public MineAdapter(Context context){
            mContext = context;
            mRand = new Random();
            createMines();
        }

        public void setGameOver(int position){
            gameOver = true;
            mMines.set(position, 1);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(56, 56));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(R.drawable.mine_button);
            if(gameOver){
                if(mMines.get(position) == 1){
                    imageView.setImageResource(R.drawable.mine);
                }
            }

            return imageView;
        }

        private void createMines(){
            for(int i = 0; i < getCount(); i++){
                mMines.add(mRand.nextInt(2));
            }
        }
    }
}
