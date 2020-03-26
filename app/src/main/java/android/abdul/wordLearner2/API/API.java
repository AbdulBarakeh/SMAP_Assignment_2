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

import java.util.HashMap;
import java.util.List;

public class API extends Volley{
    private final String Token = "Token f161a4938824d1cf79c89edce6cb6815f0e51cb8";
    public void parseJason(final WordLearnerService service, final String word){
        final String URL = "https://owlbot.info/api/v4/dictionary/owl" + word;
        RequestQueue queue = Volley.newRequestQueue(service);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                ApiWord receivedWord = gson.fromJson(response.toString(),ApiWord.class);
                List<Definition> definitions = receivedWord.getDefinitions();
                String image = "";
                String def = "";
                for (Definition current : definitions){
                    if (!current.getImageUrl().equals("")){
                        image = current.getImageUrl();
                    }
                    if (!current.getDefinition().equals("")){
                        def = current.getDefinition();
                    }
                }
                WordEntity parsedWord = new WordEntity(image,receivedWord.getWord(),receivedWord.getPronunciation(),def,"",0);
                service.addApiWord(parsedWord);
            }
        } ,null);
    queue.add(request);
    }
    public void header() {
        HashMap<String, String> head = new HashMap<>();
        head.put("Authorization",Token);
    }
}
