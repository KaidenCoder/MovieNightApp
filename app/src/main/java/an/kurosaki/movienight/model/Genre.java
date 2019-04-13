package an.kurosaki.movienight.model;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id") private Integer id;
    @SerializedName("name") private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
