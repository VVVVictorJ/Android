package com.example.admin.lockscreendemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.lockscreendemo.R;

import java.util.ArrayList;

/**
 * Created by MacaoPark on 2016/10/22.
 */

public class LockView extends RelativeLayout {
    private final ArrayList<View> viewList;
    private View rootView;
    private ImageView img;

    String TAG1 = "MainActivity";
    String TAG2 = "sainActivity";
    private ImageView leftImageView;
    private ImageView rightImageView;
    private int width;
    private int mx, my;
    private int top;
    private int bottom;
    private int left;
    private int right;
    private int leftImageView_left;
    private int rightImageView_right;
    private boolean left_flag = false;
    private boolean right_flag = false;
    private int leftImageView_right;
    private int rightImageView_left;
    private Context mContext;
    private ViewPager viewpager;
    private View view1, view2, view3;
    private TextView textView;
    public LockView(Context context) {
        super(context);

        mContext = context;
        SharedPreferences sp = mContext.getSharedPreferences("phonesize", Context.MODE_PRIVATE);
        width= sp.getInt("width", 0);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.home, this);
        LayoutInflater dd = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view1 = dd.inflate(R.layout.layout1, null);
        view2 = dd.inflate(R.layout.layout2,null);
        view3 = dd.inflate(R.layout.layout3, null);

      viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        initViews();
        addListener();
    }


    private void addListener() {
        img.setOnTouchListener(new OnTouchListener() {
            private int height;

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        // leftImageView_left:32rightImageView_right:688
                        leftImageView_left = leftImageView.getLeft();
                        leftImageView_right = leftImageView.getRight();
                        rightImageView_right = rightImageView.getRight();
                        rightImageView_left = rightImageView.getLeft();
                        System.out.println("leftImageView_left:" + leftImageView_left + "rightImageView_right:" + rightImageView_right);
                        img.setImageDrawable(getResources().getDrawable(R.drawable.lock_slide_icon_pressed));
                        height = (int) (event.getRawY() - 50);
                        System.out.println("v.getTop()" + v.getTop() + "event.getBottom()" + v.getBottom() + "v.getLeft()" + v.getLeft() + "v.getRight" + v.getRight());
                        left = v.getLeft();
                        right = v.getRight();
                        top = v.getTop();
                        bottom = v.getBottom();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mx = (int) (event.getRawX());
                        my = (int) (event.getRawY() - 50);
                        Log.i(TAG1, "  mx " + mx + "   my" + my + "  img.getWidth()/2" + img.getWidth() / 2 + "   img.getHeight()/2" + img.getHeight() / 2);
                        if (mx < width / 2) {
                            if (mx < leftImageView_right) {
                                v.layout(leftImageView_left, top, leftImageView_right, bottom);
                                left_flag = true;
                            } else {
                                v.layout(mx - img.getWidth() / 2, top, mx + img.getWidth() / 2, bottom);
                                left_flag = false;
                            }

                        } else if (mx > width / 2) {
                            if ((mx + img.getWidth() / 2) < rightImageView_right) {
                                v.layout(mx - img.getWidth() / 2, top, mx + img.getWidth() / 2, bottom);
                                Log.i(TAG2, "  int l " + (mx - img.getWidth() / 2) + "   int top" + (my - img.getHeight() / 2) + "    int right" + (mx + img.getWidth() / 2)
                                        + "   int bottom" + (my + img.getHeight() / 2));
                            }// 688

                            if (mx > rightImageView_left) {
                                v.layout(rightImageView_left, top, rightImageView_right, bottom);
                                right_flag = true;
                            } else {
                                v.layout(mx - img.getWidth() / 2, top, mx + img.getWidth() / 2, bottom);
                                right_flag = false;
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:

                        if (right_flag) {
                            Toast.makeText(mContext, "解锁右边", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(mContext, MyService.class);
                            i.setAction(MyService.UNLOCK_ACTION);
                            mContext.startService(i);

                        } else if (left_flag) {
                            Toast.makeText(mContext, "解锁左边", Toast.LENGTH_SHORT).show();
                            Intent i2 = new Intent(mContext, MyService.class);
                            i2.setAction(MyService.UNLOCK_ACTION);
                            mContext.startService(i2);
                        }
                        right_flag = false;
                        left_flag = false;
                        v.layout(left, top, right, bottom);
                        img.setImageDrawable(getResources().getDrawable(R.drawable.lock_slide_icon_normal_no_quick_launcher));
                        break;
                }

                return true;
            }
        });
    }

    private void initViews() {
        viewpager = (ViewPager) findViewById(R.id.verticalviewpager);
        leftImageView = (ImageView) rootView.findViewById(R.id.iv_left);
        rightImageView = (ImageView)rootView. findViewById(R.id.iv_right);
        img = (ImageView) rootView.findViewById(R.id.iv_drag);
        DummyAdapter dummyAdapter = new DummyAdapter();
        viewpager.setAdapter(dummyAdapter);
        textView=(TextView) findViewById(R.id.poem);//在这里加textview                               Done
        //textView.setText("hello");
        //先创建数据库
        //

    }
    public class DummyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size() ;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
