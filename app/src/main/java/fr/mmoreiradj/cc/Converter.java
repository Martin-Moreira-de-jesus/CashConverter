package fr.mmoreiradj.cc;

import android.content.Context;

public abstract class Converter {
    public float rate = -1f;

    public abstract void init(Context context);

    public float getRate() {
        return this.rate;
    }

    public abstract void setListener(ConverterListener listener);

    public abstract float convert(float valueToConvert);
}
