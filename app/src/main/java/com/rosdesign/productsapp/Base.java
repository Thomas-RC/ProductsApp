package com.rosdesign.productsapp;

import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Główna klasa Activity po niej dziedzicza wszystkie Activity aplikacji
 *
 */
public class Base extends AppCompatActivity
{

    /**
     * Obsługa błędów z API z wykorzystaniem Volley: https://developer.android.com/training/volley/request-custom#java
     * @param volleyError
     * @param arrayInput
     * @param nameBox
     * @param context
     */
    public void networkErrorResponse(VolleyError volleyError, String[] arrayInput, TextInputLayout[] nameBox, Base context)
    {
        String jsonError = "";

        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null && networkResponse.data != null)
        {
            jsonError = new String(networkResponse.data);
            Log.d("Error base: ", jsonError);
            try
            {
                JSONObject objErr = new JSONObject(jsonError);
                JSONObject dataErr = objErr.getJSONObject("data");

                for (int i=0;i<=arrayInput.length;i++)
                {
                    if(dataErr.has(arrayInput[i]) && !dataErr.getString(arrayInput[i]).equals(""))
                    {
                        if(!arrayInput[i].equals("error"))
                        {
                            nameBox[i].setError(dataErr.getString(arrayInput[i]).replaceAll("^\\[\"|\\\"]$",""));
                            nameBox[i].requestFocus();
                        }
                        else
                        {
                            Toast.makeText(context, dataErr.getString("error"), Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

}
