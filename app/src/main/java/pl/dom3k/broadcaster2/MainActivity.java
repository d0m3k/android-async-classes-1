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
            int counter = 0;
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("pl.dom3k.broadcaster2.broadcast.MY_NOTIFICATION");
                intent.putExtra("data","Notice me senpai!");
                sendBroadcast(intent);

                TextView textView = findViewById(R.id.textView);
                textView.setText("Notified Senpai " + ++counter + " times.");
            }
        });
    }
}
