package com.gowil.zzleep.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gowil.zzleep.zzleep.utils.Callback;

/**
 * Created by YaguarRuna on 28/06/2017.
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private ProgressDialog dialog;
    Callback callback;
    public void setCallback(Callback callback){
        this.callback = callback;
    }
    public DownloadTask(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }
    @Override
    protected void onPreExecute() {
        dialog.setMessage("Cargando recursos...");
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        String path = context.getFilesDir().getPath();
        try {
            String tmpDir=path+"/"+sUrl[1];
            java.io.File file = new java.io.File(tmpDir);
            if (file.exists()) {
                return "Existe";
            }

            URL url;
            url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();


            output = new FileOutputStream(tmpDir);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(callback!=null) callback.onFinish();
        super.onPostExecute(s);
    }
}