package com.hexadecimal.downloadingwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    public class DownloadTask extends AsyncTask<String, Void, String>{  // with this parameter types we can pass in
                                                                        // and pass out String variables / url in > html out /
        /* internetten veri indirme gibi buyuk boyutlu islemleri
         ana thread'in disinda yapmak daha mantıklı. Nedeni; verilerin
        indirildigi sırada baska hicbir islem yapilamaz.
        */

        @Override
        protected String doInBackground(String... urls) {
            // Strings... strings is like working with array / var args / think like it is string array
            // with Strings... strings, we can pass more than one url
            // like task.execute("www.zappycode.com","www.google.com);

            String result = null;
            URL url;
            HttpURLConnection urlConnection = null;  // HttpURLConnection bir browser gibi dusunulebilir

            // yanlis bir url girilmesine karsi try-catch bloguna aldik ornek; "zappycode.com"
            try {
                url = new URL(urls[0]);
                urlConnection= (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();        // InputStream verilen URL'den gelen cevaplari toplar
                InputStreamReader reader = new InputStreamReader(in);   // gelen cevaplari okur
                int data = reader.read();                               // karakter karakter okur

                while (data != -1){

                    char current = (char) data;
                    result += current;              // harf harf okur

                    data = reader.read();
                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task = new DownloadTask();
        String result = null;
        try{
            result = task.execute("https://zappycode.com").get();   // .get(); kullanmamizin nedeni gelen cevabi alabilmek
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
