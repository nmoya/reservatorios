package com.nikolasmoya.reservatorios;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity implements HttpRequestListener
{

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        private HttpRequestListener _listener;

        public MyGestureListener(HttpRequestListener listener)
        {
            _listener = listener;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            refresh(_listener);
            return super.onDoubleTap(e);
        }
    }

    private ProgressDialog _dialog;
    private GestureDetectorCompat _gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _dialog = new ProgressDialog(MainActivity.this);
        _dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        _dialog.setMessage(getString(R.string.http_loading_dialog));
        _dialog.setIndeterminate(true);
        _dialog.setCanceledOnTouchOutside(false);

        _gestureDetector = new GestureDetectorCompat(this, new MyGestureListener(this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this._gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void refresh(HttpRequestListener listener)
    {
        new Remote(listener).execute("http://www.ons.org.br/tabela_reservatorios/conteudo.asp");
        _dialog.show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refresh(this);
    }

    @Override
    public void onHttpResponse(Document response)
    {
        TextView _north, _northeast, _southeast, _south;
        TextView _lastUpdate;
        _dialog.dismiss();
        _north = (TextView) findViewById(R.id.text_north);
        _northeast = (TextView) findViewById(R.id.text_northeast);
        _southeast = (TextView) findViewById(R.id.text_southeast);
        _south = (TextView) findViewById(R.id.text_south);
        _lastUpdate = (TextView) findViewById(R.id.text_timestamp);

        Elements headers = response.select("h2");
        for (Element h : headers)
        {
            String[] segments = h.text().split(" ");
            String region = segments[1];
            String percentage = segments[segments.length - 1].replace(")", "");

            switch (region)
            {
                case Regions.NORTH:
                    _north.setText(percentage);
                    break;
                case Regions.NORTHEAST:
                    _northeast.setText(percentage);
                    break;
                case Regions.SOUTH:
                    _south.setText(percentage);
                    break;
                case Regions.SOUTHEAST:
                    _southeast.setText(percentage);
                    break;
            }
        }
        _lastUpdate.setText("Atualizado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
    }
}
