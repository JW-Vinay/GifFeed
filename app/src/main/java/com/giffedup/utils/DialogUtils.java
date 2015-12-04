package com.giffedup.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.giffedup.R;

/**
 * Created by vinayr on 11/28/15.
 */
public class DialogUtils {

    public static AlertDialog showDialog(Context context, int title) {
        return showDialog(context, title, -1);
    }

    public static AlertDialog showDialog(Context context, int title, int message) {
        return showDialog(context, title, message, R.string.btn_ok, null);
    }

    public static AlertDialog showDialog(Context context, int title, int message, int positiveBtn, final DialogClickListener dialogClickListener) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogClickListener != null) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        dialog.dismiss();
                        dialogClickListener.onPositiveBtnClick();
                    }
                    else if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss();
                        dialogClickListener.onNegativeBtnClick();
                    }

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.compatDialogStyle);
        builder.setTitle(title);
        if (message != -1) ;
        builder.setMessage(message);
        builder.setPositiveButton(positiveBtn, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.show();
    }

    public static AlertDialog showDialog(Context context, String title, String message, int positiveBtn, final DialogClickListener dialogClickListener) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogClickListener != null) {
                    if (which == DialogInterface.BUTTON_POSITIVE){
                        dialog.dismiss();
                        dialogClickListener.onPositiveBtnClick();
                    }
                    else if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss();
                        dialogClickListener.onNegativeBtnClick();
                    }

                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.compatDialogStyle);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(message)) ;
        builder.setMessage(message);
        builder.setPositiveButton(positiveBtn, listener);
        builder.setNegativeButton(R.string.btn_cancel, listener);
        return builder.show();
    }

    public static AlertDialog showErrorDialog(Context context, int title, int message) {
        return showErrorDialog(context, title, message, null);
    }

    public static AlertDialog showErrorDialog(Context context, int title, int message, final DialogClickListener dialogClickListener) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogClickListener != null) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        dialog.dismiss();
                        dialogClickListener.onPositiveBtnClick();
                    }
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.compatDialogStyle);
        builder.setTitle(title);
        if (message != -1) ;
            builder.setMessage(message);
        builder.setPositiveButton(R.string.btn_ok, listener);
        return builder.show();
    }
}
