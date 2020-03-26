package android.abdul.wordLearner2.API;

import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.service.WordLearnerService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API{
    private final String Token = "Token f161a4938824d1cf79c89edce6cb6815f0e51cb8";
    public void parseJason(final WordLearnerService service, final String word){
        final String URL = "https://owlbot.info/api/v4/dictionary/" + word;
        RequestQueue queue = Volley.newRequestQueue(service);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET , URL , null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ApiWord receivedWord = gson.fromJson(response.toString() , ApiWord.class);
                List<Definition> definitions = receivedWord.getDefinitions();
                String image = "";
                String def = "";
                for (Definition current : definitions) {
                    if (!current.getImageUrl().equals("")) {
                        image = current.getImageUrl();
                    }
                    if (!current.getDefinition().equals("")) {
                        def = current.getDefinition();
                    }
                }
                WordEntity parsedWord = new WordEntity(image , receivedWord.getWord() , receivedWord.getPronunciation() , def , "" , 0);
                service.addApiWord(parsedWord);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> head = new HashMap<String, String>();
                head.put("Authorization" , Token);
                return head;
            }
        };
        queue.add(request);
    }


}
