package com.jfranco.volleyexample.dto;

import java.io.Serializable;

/**
 * Created by sistemas on 29/10/2018.
 */

public class Album implements Serializable {

    private Long userId;
    private Long id;
    private String title;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
