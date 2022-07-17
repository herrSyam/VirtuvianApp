package com.example.virtuvianapplication.model;

import java.util.Date;

public class UserAccessToken {
    private String id;
    private String tokenable_type;
    private String tokenable_id;
    private String name;
    private String token;
    private String abilities;
    private Date created_at;
    private Date updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenable_type() {
        return tokenable_type;
    }

    public void setTokenable_type(String tokenable_type) {
        this.tokenable_type = tokenable_type;
    }

    public String getTokenable_id() {
        return tokenable_id;
    }

    public void setTokenable_id(String tokenable_id) {
        this.tokenable_id = tokenable_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
