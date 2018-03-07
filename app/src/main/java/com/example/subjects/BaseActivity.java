package com.example.subjects;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by vivek on 07/03/18.
 */

public class BaseActivity extends AppCompatActivity {

    void makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
