package com.devhen.volleykullanimi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //kisiEkle("silinecek","sdaf4545")
        //kisiGuncelle(8,"Gokmen","45313541523")
        //kisiSil(9)
        //tumKisiler()
        kisiAra("ds")
    }

    fun kisiSil(kisi_id: Int) {
        val url = "https://jhenimex.000webhostapp.com/services/delete_kisiler.php"
        val istek = object : StringRequest(Method.POST, url,
                Response.Listener { cevap -> Log.e("Silme Sonucu :", cevap) },
                Response.ErrorListener { error -> error.printStackTrace() }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kisi_id"] = kisi_id.toString()
                return params
            }
        }
        Volley.newRequestQueue(this@MainActivity).add(istek)
    }

    fun kisiEkle(kisi_ad: String, kisi_tel: String) {
        val url = "https://jhenimex.000webhostapp.com/services/insert_kisiler.php"
        val istek = object : StringRequest(Method.POST, url,
                Response.Listener { cevap -> Log.e("Ekleme Sonucu :", cevap) },
                Response.ErrorListener { error -> error.printStackTrace() }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kisi_ad"] = kisi_ad
                params["kisi_tel"] = kisi_tel
                return params
            }
        }
        Volley.newRequestQueue(this@MainActivity).add(istek)
    }

    fun kisiGuncelle(kisi_id: Int, kisi_ad: String, kisi_tel: String) {
        val url = "https://jhenimex.000webhostapp.com/services/update_kisiler.php"
        val istek = object : StringRequest(Method.POST, url,
                { cevap -> Log.e("Güncelleme Sonucu :", cevap) },
                { error -> error.printStackTrace() }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kisi_id"] = kisi_id.toString()
                params["kisi_ad"] = kisi_ad
                params["kisi_tel"] = kisi_tel
                return params
            }
        }
        Volley.newRequestQueue(this@MainActivity).add(istek)
    }

    fun tumKisiler() {
        val url = "https://jhenimex.000webhostapp.com/services/tum_kisiler.php"
        val istek = StringRequest(Request.Method.GET, url,
                { response ->
                    //Log.e("Veri Okuma :", response)
                    try {
                        val jsonObject = JSONObject(response)
                        val kisilerListe = jsonObject.getJSONArray("kisiler")
                        for (i in 0 until kisilerListe.length()) {
                            val k = kisilerListe.getJSONObject(i)

                            val kisi_id = k.getInt("kisi_id")
                            val kisi_ad = k.getString("kisi_ad")
                            val kisi_tel = k.getString("kisi_tel")

                            Log.e("********", "*********")
                            Log.e("ID  : ", kisi_id.toString())
                            Log.e("AD  : ", kisi_ad)
                            Log.e("TEL : ", kisi_tel)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                { error -> error.printStackTrace() })
        Volley.newRequestQueue(this@MainActivity).add(istek)
    }

    fun kisiAra(kisi_ad: String) {
        val url = "https://jhenimex.000webhostapp.com/services/tum_kisiler_arama.php"
        val istek = object : StringRequest(Method.POST, url,
                { response ->
                    jsonaCevir(response)
                },
                { error -> error.printStackTrace() }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["kisi_ad"] = kisi_ad
                return params
            }
        }
        Volley.newRequestQueue(this@MainActivity).add(istek)
    }

    fun jsonaCevir(response: String){
        try {
            val jsonObject = JSONObject(response)
            val aramaListe = jsonObject.getJSONArray("kisiler")
            for (i in 0 until aramaListe.length()){
                val k = aramaListe.getJSONObject(i)

                val kisi_id = k.getInt("kisi_id")
                val kisi_ad = k.getString("kisi_ad")
                val kisi_tel = k.getString("kisi_tel")

                Log.e("********", "*********")
                Log.e("ID  : ", kisi_id.toString())
                Log.e("AD  : ", kisi_ad)
                Log.e("TEL : ", kisi_tel)
            }
        }catch (e:JSONException){
            e.printStackTrace()
        }
    }
}