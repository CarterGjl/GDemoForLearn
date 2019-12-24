package com.tution.demolayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        ConstraintLayout constraintLayout = new ConstraintLayout(this);

        Button button = new Button(this);
        button.setText("ok");
        constraintLayout.addView(button);

        Button button1 = new Button(this);

        constraintLayout.addView(button1);
        ConstraintSet set = new ConstraintSet();

        set.connect(button.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,12);
        set.constrainHeight(button.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(button1.getId(), ConstraintSet.WRAP_CONTENT);




    }
}
