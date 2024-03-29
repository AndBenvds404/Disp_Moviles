package com.example.dispositivosmoviles.data.connections;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dispositivosmoviles.data.apikey.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Gpt3API {
    private static final String BASE_URL = "https://api.openai.com/v1/chat/completions";

    private RequestQueue queue;

    public Gpt3API(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void generateText(String prompt, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "gpt-3.5-turbo");
            jsonBody.put("temperature", 1);

            JSONArray jsonArrayMessages = new JSONArray();
            JSONObject jsonObjectMessages = new JSONObject();
            jsonObjectMessages.put("role", "user");
            jsonObjectMessages.put("content", prompt);
            jsonArrayMessages.put(jsonObjectMessages);

            jsonBody.put("messages", jsonArrayMessages);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL, jsonBody,
                    response -> {
                        try {
                            JSONObject message = response.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message");
                            String text = message.getString("content");
                            listener.onResponse(text);
                        } catch (JSONException e) {
                            Log.e("Gpt3Api", "Error parseando respuesta", e);
                            errorListener.onErrorResponse(new VolleyError("Error parseando respuesta"));
                        }
                    },
                    errorListener) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + Config.CHATAPI_KEY);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(request);
        } catch (JSONException e) {
            Log.e("Gpt3Api", "Error creando request body", e);
            errorListener.onErrorResponse(new VolleyError("Error creando request"));
        }
    }
}