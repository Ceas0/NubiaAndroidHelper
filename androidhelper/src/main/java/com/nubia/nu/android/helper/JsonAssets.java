package com.nubia.nu.android.helper;
import android.app.Activity;
import java.io.InputStream;
import java.io.IOException;
import com.nubia.nu.android.helper.utils.StreamUtil;
import org.json.JSONArray;

/**
 * Nubia devs
 * @author: Carlos Eduardo
 * @date: 09/10/22
 * -----------------------
 * Responsavel pela leitura 
 * dos JSON dentro dos assets.
 **/

public class JsonAssets{

    private final Activity activity;

    public JsonAssets(Activity activity){
        if(activity == null){
            throw new IllegalArgumentException("Activity cannot be null");
        }
        this.activity = activity;
    }

    public Result getObject(String name){
        try{
            return new Result(getString(name));
        }catch(IOException e){
            return new Result(e);
        }
    }

    public Result getArray(String name){
        return getArray(name, 0);
    }

    public Result getArray(String name, int length){
        try{
            String json = getString(name);
            if(length > 0){
                JSONArray ja = new JSONArray(json);
                while(ja.length() > length) ja.remove(ja.length() - 1);
                return new Result(ja.toString());
            }
            return new Result(json);
        }catch(Exception e){
            return new Result(e);
        }
    } 

    private String getString(String name) throws IOException{
        InputStream is = activity.getAssets().open(name);
        String json = StreamUtil.toString(is);
        StreamUtil.close(is);
        return json;
    }

    public static class Result{
        private final Throwable erro;
        private final String data;

        public Result(String data){
            this.data = data;
            this.erro = null;
        }

        public Result(Throwable erro){
            this.erro = erro;
            this.data = null;
        }

        public Throwable getErro(){
            return erro;
        }

        public String getData(){
            return data;
        }

        public boolean isSuccess(){
            return erro == null;
        }
    }

}
