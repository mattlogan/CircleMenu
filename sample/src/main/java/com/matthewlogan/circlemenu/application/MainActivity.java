package com.matthewlogan.circlemenu.application;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.matthewlogan.circlemenu.library.CircleMenu;


public class MainActivity extends Activity
        implements CircleMenu.OnItemClickListener, View.OnClickListener {

    private int mCurrentPosition;

    private int[] mBackgroundImages = new int[] {
            R.drawable.hawk_landing_on_post,
            R.drawable.walla_walla_skateboarder,
            R.drawable.windmills
    };

    private String[] mBackgroundImageNames = new String[] {
            "Hawk Landing on Post",
            "Walla Walla Skateboarder",
            "Windmills"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMenuButton().setOnClickListener(this);

        getCircleMenu().setOnItemClickListener(this);
        getCircleMenu().setItems(mBackgroundImageNames);

        if (savedInstanceState != null) {
            setBackgroundImage(savedInstanceState.getInt("currentPosition"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentPosition", mCurrentPosition);
        super.onSaveInstanceState(outState);
    }

    // Menu item click
    @Override
    public void onItemClick(int position) {
        setBackgroundImage(position);
    }

    // Menu toggle click
    @Override
    public void onClick(View v) {
        getCircleMenu().toggle();
    }

    private void setBackgroundImage(int position) {
        getBackgroundImage().setImageDrawable(
                getResources().getDrawable(mBackgroundImages[position]));
        mCurrentPosition = position;
    }

    private View getMenuButton() {
        return findViewById(R.id.menu_button);
    }

    private CircleMenu getCircleMenu() {
        return (CircleMenu) findViewById(R.id.circle_menu);
    }

    private ImageView getBackgroundImage() {
        return (ImageView) findViewById(R.id.background_image);
    }
}
