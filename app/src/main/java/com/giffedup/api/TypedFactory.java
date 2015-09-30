package com.giffedup.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by vinay-r on 8/10/15.
 */
public class TypedFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {

        return new TypeAdapter<T>() {

            final TypeAdapter<T> delegate =  gson.getDelegateAdapter(TypedFactory.this, type);
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementAdapter.read(in);
                if(jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if(jsonObject != null && jsonObject.has("images") && jsonObject.get("images").isJsonObject()) {
                        jsonElement = jsonObject.get("images");
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}
