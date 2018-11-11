package com.example.jangid.androidutilsexample;

// TODO: 9/23/2018  auto generate java code

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Utils {
    // TODO: 9/23/2018

    /**
     * show dialog
     *
     * @param ctx  ctc
     * @param msg  msg
     * @param btn1 btt1
     * @param btn2 btn2
     * @return AlertDialog;
     */

    public static AlertDialog showDialog(Context ctx, String msg, String btn1, String btn2
            , DialogInterface.OnClickListener listener1, DialogInterface.OnClickListener listener2) {
        AlertDialog.Builder ab = new AlertDialog.Builder(ctx);
        ab.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(btn1, listener1)
                .setNegativeButton(btn2, listener2);
        AlertDialog alertDialog = ab.create();
        alertDialog.show();
        return alertDialog;
    }

    /**
     * show dialog
     *
     * @param ctx  ctc
     * @param msg  msg
     * @param btn1 btt1
     * @param btn2 btn2
     * @return AlertDialog;
     */
    public static AlertDialog showDialog(Context ctx, String msg, String btn1,
                                         String btn2, DialogInterface.OnClickListener listener1) {
        return showDialog(ctx, msg, btn1, btn2, listener1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * show dialog
     *
     * @param ctx       ctc
     * @param msg       msg
     * @param listener1 listener1
     */

    public static AlertDialog showDialog(Context ctx, String msg, DialogInterface.OnClickListener listener1) {
        return showDialog(ctx, msg, ctx.getString(R.string.lbl_ok), null
                , listener1, null);
    }

    /**
     * show dialog
     *
     * @param ctx ctc
     * @param msg msg
     */


    public static AlertDialog showDialog(Context ctx, String msg) {
        return showDialog(ctx, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * @param ctx ctx*/

    public static AlertDialog showNoInternetConnection(Context ctx){
        return showDialog(ctx,ctx.getString(R.string.lbl_no_internet_connection));
    }


}
