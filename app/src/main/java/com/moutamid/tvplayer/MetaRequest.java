package com.moutamid.tvplayer;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.fxn.stash.Stash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class MetaRequest  extends JsonRequest {

    public MetaRequest(String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener) {
        super(url, requestBody, listener, errorListener);
    }

    public MetaRequest(int method, String url, @Nullable String requestBody, Response.Listener listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        Stash.put("SeassionHeader", response.headers.get("Session"));
        return Response.success(response,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    /*public MetaRequest(String url, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public MetaRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }*/



    /*@Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.d("htmlTAG", "data " + response.data);
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonResponse = new JSONObject(response.headers);
            List<Header> headers = response.allHeaders;
            Stash.put("headers", headers);
            Stash.put("SeassionHeader", response.headers.get("Session"));
            jsonResponse.put("headers", new JSONObject(response.headers));
            return Response.success(jsonResponse,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d("htmlTAG", "eee " + e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            Log.d("htmlTAG", "jee " + je.getMessage());
            return Response.error(new ParseError(je));
        }
    }*/
}
