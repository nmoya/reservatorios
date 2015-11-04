package com.nikolasmoya.reservatorios;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity implements HttpRequestListener
{

    private TextView _north, _northeast, _southeast, _south;
    private TextView _lastUpdate;
    private ProgressDialog _dialog;

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

        new Remote(this).execute("http://www.ons.org.br/tabela_reservatorios/conteudo.asp");
        _dialog.show();
    }

    @Override
    public void onHttpResponse(Document response)
    {
//        _dialog.dismiss();
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

        _lastUpdate.setText("Atualizado em: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime()));
    }
}
