package pl.dom3k.broadcaster2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO - if we only notified someone about something here...
            }
        });

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            EditText textToAdd = findViewById(R.id.text_to_add);

            @Override
            public void onClick(View view) {
//                TODO – do something to add data for content provider
            }
        });
    }
}
