package com.requisicoes.cursoandroid.requisicoeshttpcep;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.requisicoes.cursoandroid.requisicoeshttpcep.api.CEPService;
import com.requisicoes.cursoandroid.requisicoeshttpcep.model.CEP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoresultado;
    private EditText editCep;
    private TextView textlogradouro, textComplemento, textCep, textUF, textBairro, textLocalidade;
    private Retrofit retrofit;

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

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recuperarCepRetrofit();



     /*           MyTask task = new MyTask();
                 //String urlApi = "https://blockchain.info/ticker";
                String moeda = "BRL";
                // String moeda = editCep.getText().toString();
                String urlApi = " https://blockchain.info/tobtc?currency="+moeda+"&value=500";
               // String cep = editCep.getText().toString();
                *//*if(!cep.isEmpty()){

                    cep = cep.replaceAll("[^0-9]","");
                    if(cep.length()==8){
                        String urlCep = "https://viacep.com.br/ws/"+ cep + "/json/";
                        task.execute(urlCep);
                    }else{
                        Toast.makeText(MainActivity.this, "formato CEP inválido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Preencher CEP", Toast.LENGTH_SHORT).show();
                }*//*

                task.execute(urlApi);*/


            }

        });


        }

        private void recuperarCepRetrofit(){

            CEPService cepService = retrofit.create(CEPService.class);
            Call<CEP> call = cepService.recuperarCep();

            call.enqueue(new Callback<CEP>() {
                @Override
                public void onResponse(Call<CEP> call, Response<CEP> response) {
                    if(response.isSuccessful()){
                        CEP cep = response.body();
                        textoresultado.setVisibility(View.VISIBLE);
                        textoresultado.setText(cep.getLocalidade()+" / "+cep.getBairro());

                    }

                }

                @Override
                public void onFailure(Call<CEP> call, Throwable t) {

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
            Double valorMoeda = null;
            String objetoValor = null;
            String simbolo = null;

        /*    try {
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
            textUF.setText(uf);*/


            try {
                JSONObject jsonObject = new JSONObject(s);
                objetoValor = jsonObject.getString("BRL");

                JSONObject jsonObjectReal = new JSONObject(objetoValor);
                valorMoeda = jsonObjectReal.getDouble("last");
                simbolo = jsonObjectReal.getString("symbol");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            textoresultado.setVisibility(View.VISIBLE);
            textoresultado.setText(simbolo +" "+valorMoeda);
            //textoresultado.setText(objetoValor);


        }



    }

}
