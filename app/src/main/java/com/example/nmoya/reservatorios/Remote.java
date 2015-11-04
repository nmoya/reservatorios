package com.example.nmoya.reservatorios;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by nmoya on 7/20/15.
 */
class Remote extends AsyncTask<String, Void, String[]> {

    private Exception exception;
    private TextView norte, nordeste, sudeste, sul;

    public Remote(TextView norte, TextView nordeste, TextView sudeste, TextView sul)
    {
        this.norte = norte;
        this.nordeste = nordeste;
        this.sudeste = sudeste;
        this.sul = sul;
    }

    protected String[] doInBackground(String... urls) {
        String[] percentages = new String[4];
        int i = 0;

        try {
            Document doc;
            try {
                doc = Jsoup.connect(urls[0]).get();

                Elements headers = doc.select("h2");
                for (Element h : headers) {
                    String content = h.text();
                    String[] segments = content.split(" ");
                    String percentage = segments[segments.length-1];
                    percentage = percentage.replace(")", "");

                    percentages[i] = percentage;
                    i++;

                    Log.v("Headers:", percentage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            return percentages;
        }
    }

    @Override
    protected void onPostExecute(String[] strings) {
        norte.setText(strings[3]);
        nordeste.setText(strings[2]);
        sudeste.setText(strings[0]);
        sul.setText(strings[1]);
    }
}