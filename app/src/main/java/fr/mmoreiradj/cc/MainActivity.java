package fr.mmoreiradj.cc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // protected boolean rateFetched = false;
    protected float rate = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://geoplugin.net/json.gp?base_currency=USD";

        RequestQueue queue = Volley.newRequestQueue(this);

        @SuppressLint("DefaultLocale") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        response -> {
                            try {
                                this.rate = 1/(float) response.getDouble("geoplugin_currencyConverter");
                                //this.rateFetched = true;

                                findViewById(R.id.conversion_button).setEnabled(true);

                                TextView rateTextView = findViewById(R.id.conversion_rate);
                                rateTextView.setText(String.format("Conversion Rate : %.2f", this.rate));
                                Log.d("CURRENCY ", String.valueOf(this.rate));
                            } catch (JSONException e) {
                                Log.d("CURRENCY", "ERROR " + e.getMessage());
                            }
                        }
                        , error -> {
                            Log.d("CURRENCY", "Error !");
                            Log.d("CURRENCY", error.toString());
                        });

        queue.add(jsonObjectRequest);

        Button conversionButton = findViewById(R.id.conversion_button);
        conversionButton.setOnClickListener(view -> {
            // if (!rateFetched) return;

            EditText valueToConvertTextView = findViewById(R.id.conversion_value);
            String textInput = valueToConvertTextView.getText().toString();

            float valueToConvert = Float.parseFloat(textInput);
            float result = valueToConvert * this.rate;

            TextView convertedValueTextView = findViewById(R.id.conversion_result);
            convertedValueTextView.setText(String.valueOf(result));
        });
    }
}