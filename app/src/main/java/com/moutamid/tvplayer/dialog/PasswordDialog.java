package com.moutamid.tvplayer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fxn.stash.Stash;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moutamid.tvplayer.MainActivity;
import com.moutamid.tvplayer.R;
import com.moutamid.tvplayer.SettingsActivity;

public class PasswordDialog {

    Context context;

    public PasswordDialog(Context context) {
        this.context = context;
    }

    public void show(){
        final Dialog password = new Dialog(context);
        password.requestWindowFeature(Window.FEATURE_NO_TITLE);
        password.setContentView(R.layout.password_layout);

        TextInputLayout pasw = password.findViewById(R.id.et_password);
        TextInputEditText passwordEt = password.findViewById(R.id.passwordEt);
        Button ok = password.findViewById(R.id.ok);
        Button cancel = password.findViewById(R.id.cancel);

        pasw.requestFocus();
        passwordEt.requestFocus();
        pasw.getEditText().requestFocus();

        cancel.setOnClickListener(v -> password.dismiss());

        ok.setOnClickListener(v -> {
            String s = Stash.getString("password", "");
            if (pasw.getEditText().getText().toString().equals(s)){
                context.startActivity(new Intent(context, SettingsActivity.class));
                password.dismiss();
            } else {
                Toast.makeText(context, "Password is Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        password.show();
        password.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        password.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        password.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        password.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        password.getWindow().setGravity(Gravity.CENTER);
    }

}
