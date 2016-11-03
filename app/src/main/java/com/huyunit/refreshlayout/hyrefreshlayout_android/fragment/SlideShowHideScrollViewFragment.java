package com.huyunit.refreshlayout.hyrefreshlayout_android.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.huyunit.refreshlayout.SlideHideScrollView;
import com.huyunit.refreshlayout.hyrefreshlayout_android.MainActivity;
import com.huyunit.refreshlayout.hyrefreshlayout_android.R;
import com.huyunit.refreshlayout.hyrefreshlayout_android.view.MyScrollView;
import com.huyunit.refreshlayout.hyrefreshlayout_android.view.MyTextView;

/**
 * author: bobo
 * create time: 2016/10/21 17:22
 * Email: jqbo84@163.com
 */
public class SlideShowHideScrollViewFragment extends BaseFragment implements SlideHideScrollView.BottomListener, SlideHideScrollView.onScrollListener, View.OnTouchListener {

    //顶部布局隐藏的检测距离
    private static final int TOP_DISTANCE_Y = 120;
    //默认的动画时间
    private static final int TIME_ANIMATION = 300;
    //是否在顶部布局的滑动范围内
    private boolean isInTopDistance = true;

    private ImageView img_bar;
    private TextView tv_title;
    private ImageView img_tools;
    private ImageView img_author;
    private SlideHideScrollView mScroller;
    private FrameLayout fl_top;

    private MyTextView tv_content;


    private GestureDetector mGestureDetector;

    private float viewSlop;
    //按下的y坐标
    private float lastY;
    //记录手指是否向上滑动
    private boolean isUpSlide;
    //工具栏是否是隐藏状态
    private boolean isToolHide;
    //上部布局是否是隐藏状态
    private boolean isTopHide = false;
    //动画是否结束
    private boolean isAnimationFinish = true;
    //是否已经完成测量
    private boolean isMeasured = false;

    public static SlideShowHideScrollViewFragment newInstance() {
        SlideShowHideScrollViewFragment fragment = new SlideShowHideScrollViewFragment();
        return fragment;
    }

    @Override
    protected void initView(LayoutInflater inflater, Bundle savedInstanceState) {
//        ((MainActivity) getActivity()).getSupportActionBar().hide();
        setContentView(inflater, R.layout.f_slideshowhidescrollview);
        img_bar = getViewById(R.id.img_bar);
        tv_title = getViewById(R.id.tv_title);
        tv_content = getViewById(R.id.tv_content);
        img_tools = getViewById(R.id.img_tools);
        img_author = getViewById(R.id.img_author);
        mScroller = getViewById(R.id.scroller);
        fl_top = getViewById(R.id.ll_top);

        viewSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();

        mGestureDetector = new GestureDetector(getActivity(), new DetailGestureListener());

        initListener();

        //获取Bar和Title的高度，完成auther布局的margenTop设置
        ViewTreeObserver viewTreeObserver = fl_top.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (!isMeasured) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout
                            .LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, img_bar.getHeight() + tv_title.getHeight(), 0, 0);
                    img_author.setLayoutParams(layoutParams);
                    isMeasured = true;
                }
                return true;
            }
        });
        initFooterView();
    }

    public void initListener() {
        mScroller.setBottomListener(this);
        mScroller.setScrollListener(this);
        mScroller.setOnTouchListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    /**
     * 此方法有点怪异，在Activity是不需要添加此方法，但在Fragment非得要添加此方法，否则底部FooterView不显示。
     */
    public void initFooterView(){
        System.out.println("SlideShowHideScrollViewFragment.initFooterView");
        int toobarHeight = ((MainActivity) getActivity()).getSupportActionBar().getHeight();

        int startY = getActivity().getWindow().getDecorView().getHeight() - getStatusHeight(getActivity());
        System.out.println("imgTools.showTool.startY = " + startY);
        System.out.println("imgTools.showTool.img_tools.getHeight() = " + img_tools.getHeight());
        ObjectAnimator anim = ObjectAnimator.ofFloat(img_tools, "y", startY, startY - 150 - toobarHeight);
        anim.setDuration(10);
        anim.start();
    }

    /**
     * 显示工具栏
     */
    private void showTool() {

        Log.d("TAG", "------------showTool-----------");

        int toobarHeight = ((MainActivity) getActivity()).getSupportActionBar().getHeight();
        int startY = getActivity().getWindow().getDecorView().getHeight() - getStatusHeight(getActivity());
        System.out.println("toobarHeight = " + toobarHeight);
        System.out.println("imgTools.showTool.startY = " + startY);
        System.out.println("imgTools.showTool.img_tools.getHeight() = " + img_tools.getHeight());
        ObjectAnimator anim = ObjectAnimator.ofFloat(img_tools, "y", startY, startY - img_tools.getHeight() - toobarHeight);
        anim.setDuration(TIME_ANIMATION);
        anim.start();
        isToolHide = false;

    }

    /**
     * 隐藏工具栏
     */
    private void hideTool() {

        Log.d("TAG", "------------hideTool-----------");

        int toobarHeight = ((MainActivity) getActivity()).getSupportActionBar().getHeight();
        int startY = getActivity().getWindow().getDecorView().getHeight() - getStatusHeight(getActivity());
        System.out.println("toobarHeight = " + toobarHeight);
        System.out.println("imgTools.hideTool.startY = " + startY);
        System.out.println("imgTools.hideTool.img_tools.getHeight() = " + img_tools.getHeight());
        ObjectAnimator anim = ObjectAnimator.ofFloat(img_tools, "y", startY - img_tools.getHeight() - toobarHeight, startY);
        anim.setDuration(TIME_ANIMATION);
        anim.start();
        isToolHide = true;

    }

    /**
     * 显示上部的布局
     */
    private void showTop() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(img_bar, "y", img_bar.getY(), 0);
        anim1.setDuration(TIME_ANIMATION);
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(tv_title, "y", tv_title.getY(), img_bar.getHeight());
        anim2.setInterpolator(new DecelerateInterpolator());
        anim2.setDuration(TIME_ANIMATION + 200);
        anim2.start();

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(fl_top, "y", fl_top.getY(), 0);
        anim4.setDuration(TIME_ANIMATION);
        anim4.start();

        isTopHide = false;
    }


    /**
     * 隐藏上部的布局
     */
    private void hideTop() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(img_bar, "y", 0, -img_bar.getHeight());
        anim1.setDuration(TIME_ANIMATION);
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(tv_title, "y", tv_title.getY(), -tv_title.getHeight());
        anim2.setDuration(TIME_ANIMATION);
        anim2.start();

        ObjectAnimator anim4 = ObjectAnimator.ofFloat(fl_top, "y", 0, -(img_bar.getHeight() + tv_title.getHeight()));
        anim4.setDuration(TIME_ANIMATION);
        anim4.start();

        isTopHide = true;
    }

    @Override
    public void onBottom() {
        if (isToolHide) {
            showTool();
        }
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        //判断当前的布局范围是否是在顶部布局的滑动范围内
        if (t <= dp2px(TOP_DISTANCE_Y)) {
            isInTopDistance = true;
        } else {
            isInTopDistance = false;
        }

        if (t <= dp2px(TOP_DISTANCE_Y) && isTopHide) {
            showTop();
        } else if (t > dp2px(TOP_DISTANCE_Y) && !isTopHide) {
            hideTop();
        }
    }

    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d("TAG", "mScroller-----ACTION_DOWN------------");
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("TAG", "mScroller-----ACTION_MOVE");

                float disY = event.getY() - lastY;
                //垂直方向滑动
                if (Math.abs(disY) > viewSlop) {
                    //设置了TextView的点击事件之后，会导致这里的disY的数值出现跳号现象，最终导致的效果就是
                    //下面的tool布局在手指往下滑动的时候，先显示一个，然后再隐藏，这是完全没必要的
                    Log.d("TAG", "----------------------disY = " + disY);
                    //是否向上滑动
                    isUpSlide = disY < 0;
                    //实现底部tools的显示与隐藏
                    if (isUpSlide) {
                        if (!isToolHide)
                            hideTool();
                    } else {
                        if (isToolHide)
                            showTool();
                    }
                }

                lastY = event.getY();
                break;
        }

        mGestureDetector.onTouchEvent(event);
        return false;
    }

    /**
     * 手势指示器
     */
    private class DetailGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            //如果都是隐藏状态，那么都显示出来
            if (isTopHide && isToolHide) {
                showTop();
                showTool();
            } else if (!isToolHide && isTopHide) {
                //如果上面隐藏，下面显示，就显示上面
                showTop();
            } else if (!isTopHide && isToolHide) {
                //如果上面显示，下面隐藏，那么就显示下面
                showTool();
            } else {
                //都在显示，那么就都隐藏
                hideTool();
                if (!isInTopDistance) {
                    hideTop();
                }
            }

            return super.onSingleTapConfirmed(e);
        }
    }


    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

}
