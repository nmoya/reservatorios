package com.example.nmoya.reservatorios;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    private TextView norte, nordeste, sudeste, sul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        norte = (TextView) findViewById(R.id.textViewNorte);
        nordeste= (TextView) findViewById(R.id.textViewNordeste);
        sudeste = (TextView) findViewById(R.id.textViewSudeste);
        sul = (TextView) findViewById(R.id.textViewSul);

        new Remote(norte, nordeste, sudeste, sul).execute("http://www.ons.org.br/tabela_reservatorios/conteudo.asp");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
