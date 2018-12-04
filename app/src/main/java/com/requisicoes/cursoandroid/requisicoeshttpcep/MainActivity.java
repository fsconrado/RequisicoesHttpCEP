package com.requisicoes.cursoandroid.requisicoeshttpcep;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoresultado;
    private EditText editCep;
    private TextView textlogradouro, textComplemento, textCep, textUF, textBairro, textLocalidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        textoresultado = findViewById(R.id.textRecuperar);
        editCep = findViewById(R.id.editCep);

        textlogradouro = findViewById(R.id.textLogradouro);
        textComplemento = findViewById(R.id.textComplemento);
        textCep = findViewById(R.id.textCep);
        textUF = findViewById(R.id.textUf);
        textBairro = findViewById(R.id.textBairro);
        textLocalidade = findViewById(R.id.textlocalidade);


        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask task = new MyTask();
                 //String urlApi = "https://blockchain.info/ticker";
                // String moeda = "USD";
                // String moeda = editCep.getText().toString();
                //String urlApi = " https://blockchain.info/tobtc?currency="+moeda+"&value=500";
                String cep = editCep.getText().toString();
                if(!cep.isEmpty()){

                    cep = cep.replaceAll("[^0-9]","");
                    if(cep.length()==8){
                        String urlCep = "https://viacep.com.br/ws/"+ cep + "/json/";
                        task.execute(urlCep);
                    }else{
                        Toast.makeText(MainActivity.this, "formato CEP inválido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencher CEP", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    class MyTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;
            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recuperar os dados em Bytes
                inputStream = conexao.getInputStream();

                // O inputeStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();

                String linha = "";
                while ( (linha =  reader.readLine())!=null ){
                    buffer.append(linha);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String logradouro = null;
            String cep = null;
            String complemento = null;
            String bairro = null;
            String localidade = null;
            String uf = null;

            try {
                JSONObject jsonObject = new JSONObject(s);
                logradouro = "Logradouro: " + jsonObject.getString("logradouro");
                cep = "Cep: " + jsonObject.getString("cep");
                complemento = "Complemento: " + jsonObject.getString("complemento");
                bairro = "bairro: " + jsonObject.getString("bairro");
                localidade = "Localidade: " + jsonObject.getString("localidade");
                uf = "Uf: " + jsonObject.getString("uf");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //textoresultado.setVisibility(View.VISIBLE);
            //textoresultado.setText(logradouro);
            textlogradouro.setText(logradouro);
            textCep.setText(cep);
            textComplemento.setText(complemento);
            textBairro.setText(bairro);
            textLocalidade.setText(localidade);
            textUF.setText(uf);
        }
    }

}
