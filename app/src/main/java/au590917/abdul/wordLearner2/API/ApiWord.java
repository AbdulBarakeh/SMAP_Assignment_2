package au590917.abdul.wordLearner2.API;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// inspiration comes from : https://www.youtube.com/watch?v=y2xtLqP8dSQ
//SRC: http://www.jsonschema2pojo.org/
public class ApiWord {

    @SerializedName("definitions")
    @Expose
    private List<Definition> definitions = null;
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("pronunciation")
    @Expose
    private String pronunciation;

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

}
