package fr.mmoreiradj.cc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

public class ConverterGeoPlugin extends Converter {

    protected static final String url = "http://geoplugin.net/json.gp?base_currency=USD";
    protected RequestQueue queue;

    @Override
    public void init(Context context) {
        fetchRate((MainActivity) context);
    }

    private void fetchRate(MainActivity context) {
        if (queue == null)
            queue = Volley.newRequestQueue(context);

        @SuppressLint("DefaultLocale") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,
                        url,
                        null,
                        response -> {
                            try {
                                this.rate = 1 / (float) response.getDouble("geoplugin_currencyConverter");
                                //this.rateFetched = true;

                                if (listener != null)
                                    this.listener.update(this.rate);

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
    }

    @Override
    public void setListener(ConverterListener listener) {
        this.listener = listener;
    }

    @Override
    public float convert(float valueToConvert) {
        return valueToConvert * this.rate;
    }
}
