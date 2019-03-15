package aop.demo.jetpack.android.gdemoforlearn;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;

import android.view.View;

public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Navigation.findNavController(Launcher.this,R.id.nv).navigate(R.id.blankFragment2);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this,R.id.nv).navigateUp();
    }
}
