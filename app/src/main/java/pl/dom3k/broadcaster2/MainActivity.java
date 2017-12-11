package pl.dom3k.broadcaster2;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.KEY_MESSAGE, textToAdd.getText().toString());

                getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
            }
        });
    }
}
