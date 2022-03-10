package fr.mmoreiradj.cc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements ConverterListener {
    // protected boolean rateFetched = false;
    protected Converter converter;

    protected float rate = 0f;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.converter = new ConverterGeoPlugin();
        converter.init(this);

        converter.setListener(this);

        findViewById(R.id.conversion_button).setOnClickListener(view -> {
            EditText valueToConvertTextView = findViewById(R.id.conversion_value);
            String textInput = valueToConvertTextView.getText().toString();

            float convertedValue = converter.convert(Float.parseFloat(textInput));

            TextView resultView = findViewById(R.id.conversion_result);
            resultView.setText(String.format("%.2f", convertedValue));
        });
    }

    @Override
    public void update(float val) {
        findViewById(R.id.conversion_button).setEnabled(true);
        TextView rateTextView = findViewById(R.id.conversion_rate);
        rateTextView.setText(String.format("Conversion Rate : %.2f", converter.getRate()));
    }
}