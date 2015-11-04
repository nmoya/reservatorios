package com.nikolasmoya.reservatorios;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

class Remote extends AsyncTask<String, Void, Document>
{
    private Exception exception;
    private HttpRequestListener _listener;

    public Remote(HttpRequestListener listener)
    {
        _listener = listener;
    }

    protected Document doInBackground(String... urls)
    {
        Document doc = null;
        try
        {
            doc = Jsoup.connect(urls[0]).get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            return doc;
        }
    }

    @Override
    protected void onPostExecute(Document doc)
    {
        _listener.onHttpResponse(doc);

    }
}