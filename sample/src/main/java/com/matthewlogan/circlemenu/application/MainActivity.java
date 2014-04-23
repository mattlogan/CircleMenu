package com.matthewlogan.circlemenu.application;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.matthewlogan.circlemenu.library.CircleMenu;


public class MainActivity extends Activity
        implements CircleMenu.OnItemClickListener, View.OnClickListener {

    private static int[] sBackgroundImages = new int[] {
            R.drawable.hawk_landing_on_post,
            R.drawable.walla_walla_skateboarder,
            R.drawable.windmills
    };

    private static String[] sBackgroundImageNames = new String[] {
            "Hawk Landing on Post",
            "Walla Walla Skateboarder",
            "Windmills"
    };

    private int mCurrentPosition;

    private View mMenuButton;
    private CircleMenu mCircleMenu;
    private ImageView mBackgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mMenuButton = findViewById(R.id.menu_button);
        mCircleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        mBackgroundImageView = (ImageView) findViewById(R.id.background_image);

        mMenuButton.setOnClickListener(this);

        mCircleMenu.setOnItemClickListener(this);
        mCircleMenu.setItems(sBackgroundImageNames);

        if (savedInstanceState != null) {
            setBackgroundImage(savedInstanceState.getInt("currentPosition"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPosition", mCurrentPosition);
        super.onSaveInstanceState(outState);
    }

    private void setBackgroundImage(int position) {
        mBackgroundImageView.setImageDrawable(
                getResources().getDrawable(sBackgroundImages[position]));
        mCurrentPosition = position;
    }

    // Menu item click
    @Override
    public void onItemClick(int position) {
        setBackgroundImage(position);
    }

    // Menu toggle click
    @Override
    public void onClick(View v) {
        mCircleMenu.toggle();
    }
}
