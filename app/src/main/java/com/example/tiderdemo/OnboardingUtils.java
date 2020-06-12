package com.example.tiderdemo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public interface OnboardingUtils {

    void showToolbar();

    void hideToolbar();

    void showToolbarIcon(int iconId);

    void hideToolbarIcon();

    void showSnackBarWithAction(int view, int mainTextStringId, int actionStringId, View.OnClickListener listener);

    void dismissSnackBar();

    void changeStatusBarColor(int color);

    void showToastMessage(String toastMessage);

    void showProgressBar();

    void hideProgressBar();

    void hideKeyboard(Context context, View view);

    boolean isConnected();

    void fadeOutAndHideImage(ImageView imageView);

    boolean isDeviceLocationEnabled(Context context);

    boolean arePermissionsGiven(Context context);

    void customUpButtonNav(View view);

    void checkConnection();
}
