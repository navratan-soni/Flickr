package com.nuffwritten.flickr.ui.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nuffwritten.flickr.R;
import com.nuffwritten.flickr.util.NumberChangedListener;

/**
 * Created by navratan on 2019-09-03
 */
public class CustomDialogUtil{

    public static void showNumberChangedDialog(Context context, int currentItems, NumberChangedListener listener) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.my_dialog, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        EditText et = dialogView.findViewById(R.id.edit_text);
        et.setText(String.valueOf(currentItems));

        builder.setTitle(R.string.dialog_title);
        builder.setPositiveButton("OK", (dialog, which) -> {
            int newNumber = Integer.valueOf(et.getEditableText().toString());
            if(newNumber == currentItems)
                return;
            if(newNumber < 1 || newNumber > 5) {
                Toast.makeText(context, context.getResources().getString(R.string.extract_notice), Toast.LENGTH_SHORT).show();
                return;
            }
            listener.onNumberChaged(newNumber);
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
