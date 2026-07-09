package com.texttranslate.voice.translateApi

import android.os.AsyncTask
import android.util.Log
import com.texttranslate.voice.interfaces.OnTranslationCompleteListener
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class TranslateApi : AsyncTask<String?, String?, String>() {
    private var listener: OnTranslationCompleteListener? = null

    override fun doInBackground(vararg strings: String?): String {
        val strArr = strings
        var result = ""
        try {
            val encode = URLEncoder.encode(strArr[0], "utf-8")
            val sb = StringBuilder()
            sb.append("https://translate.googleapis.com/translate_a/single?client=gtx&sl=")
            sb.append(strArr[1])
            sb.append("&tl=")
            sb.append(strArr[2])
            sb.append("&dt=t&q=")
            sb.append(encode)

            val url = URL(sb.toString())
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            con.setRequestProperty("User-Agent", "Mozilla/5.0")

            val responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(con.inputStream))
                val response = StringBuilder()
                var inputLine: String?
                while (reader.readLine().also { inputLine = it } != null) {
                    response.append(inputLine)
                }
                reader.close()

                val jSONArray = JSONArray(response.toString()).getJSONArray(0)
                val finalResult = StringBuilder()
                for (i in 0 until jSONArray.length()) {
                    val jSONArray2 = jSONArray.getJSONArray(i)
                    finalResult.append(jSONArray2.get(0).toString())
                }
                result = finalResult.toString()
            } else {
                throw Exception("Error response code: $responseCode")
            }
        } catch (e: Exception) {
            Log.e("translate_api", e.message.orEmpty())
            listener?.onError(e)
        }
        return result
    }

    override fun onPreExecute() {
        super.onPreExecute()
        listener?.onStartTranslation()
    }

    override fun onPostExecute(text: String) {
        listener?.onCompleted(text)
    }

    fun setOnTranslationCompleteListener(listener: OnTranslationCompleteListener?) {
        this.listener = listener
    }
}