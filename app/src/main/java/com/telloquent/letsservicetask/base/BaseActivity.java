package com.telloquent.letsservicetask.base;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.telloquent.letsservicetask.R;

public class BaseActivity extends AppCompatActivity {
    private Boolean activityFinishFlag;
    public boolean shouldDiscardChanges = false;
    private int busyCounter = 0;
    private final static String TAG = BaseActivity.class.getName();
    private View mNewRequestView;
    protected String mTripKey;
    TextView mTimerTextView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setupActionBar(String title, final ActionBarActivityLeftAction leftAction, final ActionBarActivityRightAction rightAction, final ActionBarActivityRight2Action right2Action,final ActionBarActivityRight3Action right3Action) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View customView = getLayoutInflater().inflate(R.layout.framework_action_bar_view, null);
        getSupportActionBar().setCustomView(customView);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        setActivityTitle(title);
        setToolbarRightIcon2(right2Action);
        setToolbarRightIcon3(right3Action);

        final ImageView leftIcon = ((ImageView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_leftIcon));
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBarLeftButtonClicked();
                if (activityFinishFlag && !shouldDiscardChanges) {
                    finish();
                } else if (shouldDiscardChanges) {
                    if (shouldDiscardChanges) {
                        showDiscardAlert();
                    }
                } else if (leftAction == ActionBarActivityLeftAction.WELCOME) {
                    //actionBarLeftButtonClicked();
                }
            }
        });
        leftIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    TypedValue outValue = new TypedValue();
                    getApplicationContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
                    ((ImageView) v).setBackgroundResource(outValue.resourceId);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((ImageView) v).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                return false;
            }
        });

        final ImageView rightIcon = ((ImageView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_rightIcon));
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightAction == ActionBarActivityRightAction.ACTION_DELETE) {
                    actionBarRightButtonClicked();
                } else if (rightAction == ActionBarActivityRightAction.ACTION_UPDATE_BUTTON) {
                    actionBarRightButtonClicked();
                } else if (rightAction == ActionBarActivityRightAction.ACTION_CLOSE) {
                    actionBarRightButtonClicked();
                    if (activityFinishFlag && !shouldDiscardChanges) {
                        finish();
                    } else if (shouldDiscardChanges) {
                        if (shouldDiscardChanges) {
                            showDiscardAlert();
                        }
                    }
                }
            }
        });
        TextView leftText = ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_left_title));

        TextView rightText = ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_rightText));
        /*Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Lato-Regular.ttf");
        rightText.setTypeface(custom_font);*/

        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right2Action == ActionBarActivityRight2Action.ACTION_DONE) {
                    actionBarRight2TextClicked();
                }

            }
        });

        rightIcon.setVisibility(View.GONE);
        leftIcon.setVisibility(View.GONE);
        leftText.setVisibility(View.GONE);
        switch (leftAction) {
            case ACTION_BACK:
                leftIcon.setImageResource(R.drawable.keyboard_back_icon);
                leftIcon.setVisibility(View.VISIBLE);
                activityFinishFlag = true;
                break;
            case ACTION_CLOSE:
                leftIcon.setVisibility(View.VISIBLE);
                leftIcon.setImageResource(R.drawable.close_icon);
                activityFinishFlag = true;
                break;
            case ACTION_NONE:
                activityFinishFlag = false;
                break;
            case WELCOME:
                leftText.setVisibility(View.VISIBLE);
                leftText.setText("Welcome!");
                leftText.setTextColor(getResources().getColor(R.color.yellow_color));
                break;
        }
        switch (rightAction) {
            case ACTION_NONE:
                break;
            case ACTION_UPDATE_BUTTON:
                rightIcon.setVisibility(View.VISIBLE);
                rightIcon.setImageResource(R.drawable.update_arrow);
                break;
            case ACTION_CLOSE:
                rightIcon.setVisibility(View.VISIBLE);
                rightIcon.setImageResource(R.drawable.close_icon);
                activityFinishFlag = true;
        }


    }

    protected void setActivityTitle(String title) {
        final TextView activityTitle = ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title));
        /*Typeface custom_title_font = Typeface.createFromAsset(getAssets(),  "fonts/Lato-Regular.ttf");
        activityTitle.setTypeface(custom_title_font);*/
        activityTitle.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        activityTitle.setText(title);
    }

    protected void setToolbarRightIcon2(final ActionBarActivityRight2Action right2Action) {
        final ImageView rightIcon2 = ((ImageView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_rightIcon2));
        rightIcon2.setVisibility(View.GONE);
        switch (right2Action) {
            case ACTION_NONE:
                break;
            case COMPANY_LOGO:
                rightIcon2.setVisibility(View.VISIBLE);


                break;
        }

    }
    protected void setToolbarRightIcon3(final ActionBarActivityRight3Action right3Action) {
        final ImageView rightIcon3 = ((ImageView) getSupportActionBar().getCustomView().findViewById(R.id.action_bar_rightIcon3));
        rightIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (right3Action == ActionBarActivityRight3Action.ACTION_RESET) {
                    actionBarRight3ButtonClicked();
                }
            }
        });
        rightIcon3.setVisibility(View.GONE);
        switch (right3Action) {
            case ACTION_NONE:
                break;
            case ACTION_RESET:
                rightIcon3.setVisibility(View.VISIBLE);
                rightIcon3.setImageResource(R.drawable.ic_reset_pass);
                break;
        }

    }


    public void showToolbar(boolean show) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }
        if (show) {
            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        } else {
            toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        }
    }

    public void actionBarLeftButtonClicked() {
        //Toast.makeText(this, "LeftIcon Clicked", Toast.LENGTH_SHORT).show();
    }

    public void actionBarRightButtonClicked() {
        //Toast.makeText(this, "RightIcon Clicked", Toast.LENGTH_SHORT).show();
    }

    public void actionBarRight3ButtonClicked() {
        //Toast.makeText(this, "Clicked Right2Icon", Toast.LENGTH_SHORT).show();
    }

    public void actionBarRight2TextClicked() {
        //Toast.makeText(this, "Clicked Right2Text", Toast.LENGTH_SHORT).show();
    }

    public void doLogoutWithAlert(Boolean withAlert) {

    }

    public enum ActionBarActivityLeftAction {
        ACTION_NONE,
        ACTION_CLOSE,
        ACTION_BACK,
        WELCOME
    }
    public enum ActionBarActivityRightAction {
        ACTION_NONE,
        ACTION_DELETE,
        ACTION_UPDATE_BUTTON,
        ACTION_CLOSE
    }

    public enum ActionBarActivityRight2Action {
        ACTION_NONE,
        ACTION_DONE,

        ACTION_RESET,
        COMPANY_LOGO,
    }

    public enum ActionBarActivityRight3Action {
        ACTION_NONE,
        ACTION_RESET,

    }

    @Override
    public void onBackPressed() {

    }

    void showDiscardAlert() {

    }
    public void showDevelopmentToastMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


}


