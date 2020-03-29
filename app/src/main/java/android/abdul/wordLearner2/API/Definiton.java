//http://www.jsonschema2pojo.org/
package android.abdul.wordLearner2.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//SRC: http://www.jsonschema2pojo.org/

class Definition {

    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
