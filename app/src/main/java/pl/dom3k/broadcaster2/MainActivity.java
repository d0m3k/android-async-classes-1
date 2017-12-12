package pl.dom3k.broadcaster2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            TextView text = findViewById(R.id.textView);
            int counter = 0;

            @Override
            public void onClick(View view) {
//                TODO – create some broadcast, and change TextView value to reflect count of actions
            }
        });
    }
}
