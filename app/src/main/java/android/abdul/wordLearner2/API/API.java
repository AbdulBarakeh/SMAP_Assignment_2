package android.abdul.wordLearner2.API;

import android.abdul.wordLearner2.database.WordEntity;
import android.abdul.wordLearner2.service.WordLearnerService;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class API extends Volley{

    public void parseJason(final WordLearnerService service, final String word){
        final String URL = "https://owlbot.info/api/v4/dictionary/owl" + word;
        final String Token = "Token f161a4938824d1cf79c89edce6cb6815f0e51cb8";
        RequestQueue queue = Volley.newRequestQueue(service);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ApiWord receivedWord = gson.fromJson(response.toString(),ApiWord.class);
                WordEntity parsedWord = new WordEntity();
//                WordEntity parsedWord = new WordEntity(receivedWord.getDefinitions().getImage_url(),
//                        receivedWord.getName(),receivedWord.getPronunciation(),
//                        receivedWord.getDefinitions().getDefinition(),"",0);
            }
        } ,null);
    queue.add(request);
    }
}
