package com.requisicoes.cursoandroid.requisicoeshttpcep.api;


import com.requisicoes.cursoandroid.requisicoeshttpcep.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by fabianoconrado
 */


public interface CEPService {

//    @GET("01310100/json/")
    @GET("57018560/json/")
    Call<CEP> recuperarCep();

    /*@GET("/fotos")
    Call<Fotos> recuperarFotos();*/

}
