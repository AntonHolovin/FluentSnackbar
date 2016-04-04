package com.golovin.sample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.golovin.snackbarmanager.SnackbarManager;
import com.golovin.snackbarmanager.SnackbarOptions;

@SuppressWarnings("ConstantConditions")
public class MainActivity extends AppCompatActivity {

    private SnackbarManager mSnackbarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSnackbarManager = new SnackbarManager(this);

        findViewById(R.id.button_show_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbarManager.showSuccess("Success");
            }
        });

        findViewById(R.id.button_show_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbarManager.showError("Error");
            }
        });

        findViewById(R.id.button_show_warning).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbarManager.showWarning("Warning");
            }
        });

        findViewById(R.id.button_show_warning_important).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbarManager.showWarning("Important warning", true);
            }
        });

        findViewById(R.id.button_show_styled).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarOptions options = new SnackbarOptions()
                        .setBackgroundColor(R.color.purple_500)
                        .setTextColor(R.color.blue_grey_500)
                        .setDuration(Snackbar.LENGTH_SHORT)
                        .setAction(R.string.action_text, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIsImportant(true);
                mSnackbarManager.show("Styled", options);
            }
        });
    }
}
