package com.matthewlogan.circlemenu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.matthewlogan.circlemenu.R;

public class CircleMenu extends FrameLayout
        implements AdapterView.OnItemClickListener, Animation.AnimationListener {

    private Context mContext;

    private ListView mListView;

    private OnItemClickListener mListener;

    private Animation mInitialHideAnimation;
    private Animation mHideAnimation;
    private Animation mShowAnimation;

    private boolean mIsAnimating = false;
    private boolean mIsShowing = true;
    
    private int mTextColor;
    private float mTextSize;

    private int mDividerColor;

    // This is the ListView's listener.  We'll use this to trigger our own.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Subtract one for the "header"
        mListener.onItemClick(position - 1);
    }

    // Whoever is implementing this class only needs to know the clicked item position.
    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    public CircleMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleMenu, 0, 0);
            if (a != null) {
                try {
                    mTextColor = a.getColor(R.styleable.CircleMenu_textColor, Color.WHITE);
                    mTextSize = a.getDimensionPixelSize(R.styleable.CircleMenu_textSize, 54);
                    mDividerColor = a.getColor(R.styleable.CircleMenu_dividerColor, Color.WHITE);
                } catch (Exception e) {
                    android.util.Log.e("CircleMenu", "Error while creating the view:", e);
                } finally {
                    a.recycle();
                }
            }
        }

        mContext = context;

        setupMenu();
        loadAnimations();

        // The view loads in it's "showing" position, and we animate it away instantaneously
        // before the view becomes visible.
        startAnimation(mInitialHideAnimation);
    }

    private void setupMenu() {
        mListView = new ListView(mContext);
        mListView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mListView.setOnItemClickListener(this);
        addView(mListView);
    }

    private void loadAnimations() {
        mInitialHideAnimation = AnimationUtils.loadAnimation(mContext,
                R.anim.circle_menu_initial_hide);
        mHideAnimation = AnimationUtils.loadAnimation(mContext, R.anim.circle_menu_hide);
        mShowAnimation = AnimationUtils.loadAnimation(mContext, R.anim.circle_menu_show);

        mInitialHideAnimation.setAnimationListener(this);
        mHideAnimation.setAnimationListener(this);
        mShowAnimation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        mIsAnimating = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mIsAnimating = false;
        mIsShowing = !mIsShowing;
        setVisibility(mIsShowing ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void setItems(String[] items) {
        // Rather than dealing with adding a header view to the ListView, we'll just add an
        // empty row.  This way, we don't have to inflate another view manually.
        String[] itemsWithHeader = new String[items.length + 1];
        for (int i = 0; i < items.length + 1; i++) {
            itemsWithHeader[i] = (i == 0) ? "" : items[i - 1];
        }

        CircleMenuAdapter<String> adapter = new CircleMenuAdapter<String>(mContext,
                R.layout.circle_menu_item, R.id.circle_menu_item_text, itemsWithHeader);
        mListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void toggle() {
        if (mIsAnimating) {
            return;
        }

        startAnimation(mIsShowing ? mHideAnimation : mShowAnimation);
    }

    private class CircleMenuAdapter<String> extends ArrayAdapter<String> {

        public CircleMenuAdapter(Context context, int resource, int textViewResourceId,
                                 String[] objects) {

            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            TextView textView = (TextView) view.findViewById(R.id.circle_menu_item_text);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            textView.setTextColor(mTextColor);

            View divider = view.findViewById(R.id.circle_menu_item_divider);
            divider.setBackgroundColor(mDividerColor);

            return view;
        }

        // Makes the top row (presumably underneath an action bar or control of some kind)
        // not clickable.
        @Override
        public boolean isEnabled(int position) {
            return !(position == 0);
        }
    }

}
