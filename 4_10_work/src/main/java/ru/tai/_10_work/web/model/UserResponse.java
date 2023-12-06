package ru.tai._10_work.web.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private List<NewsResponse> newsList = new ArrayList<>();

    public UserResponse() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserResponse;
    }

}
