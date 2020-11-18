package com.datasoft.servicea.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Country {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("isStateExist")
    private Boolean isStateExist;

    /**
     * Initialize Country object with default id
     * If id is zero, this corresponds to a null value in the database.
     * Country will not be allowed to have an id of 0.
     * @param id
     */
    public Country(Integer id) {
        this.id = id == 0? null : id;
    }

    public Country() {
    }

}