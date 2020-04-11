package au590917.abdul.wordLearner2.API;

import au590917.abdul.wordLearner2.database.WordEntity;
import au590917.abdul.wordLearner2.service.WordLearnerService;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


//Help and inspiration comes from : https://www.youtube.com/watch?v=y2xtLqP8dSQ & AU219980
//Sends request with word attached and receives response
//Inspiration from part 1 to 4. SRC: https://codinginflow.com/tutorials/android/gson/part-1-simple-serialization-deserialization
public class API{
    private final String Token = "Token f161a4938824d1cf79c89edce6cb6815f0e51cb8";
    public void parseJason(final WordLearnerService service, final String word){
        final String URL = "https://owlbot.info/api/v4/dictionary/" + word;
        RequestQueue queue = Volley.newRequestQueue(service);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET , URL , null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new GsonBuilder().serializeNulls().create();
                ApiWord receivedWord = gson.fromJson(response.toString() , ApiWord.class);
                List<Definition> definitions = receivedWord.getDefinitions();
                Definition firstIndex = definitions.get(0);
                String image = "";
                String def = "";
                if (firstIndex.getImageUrl() == null){
                    firstIndex.setImageUrl("");
                }
                if (!firstIndex.getImageUrl().equals("")) {
                    image = firstIndex.getImageUrl();
                }
                if (firstIndex.getDefinition() == null){
                    firstIndex.setDefinition("");
                }
                if (!firstIndex.getDefinition().equals("")) {
                    def = firstIndex.getDefinition();

                }
                WordEntity parsedWord = new WordEntity(image , receivedWord.getWord() , receivedWord.getPronunciation() , def , "" , 0);
                try {
                    service.addApiWord(parsedWord);
                }
                catch ( ExecutionException e ) {
                    e.printStackTrace();
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
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
