package com.paveltinnik.webquizengine.engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    private String creator;

    @NotBlank
    @NotEmpty
    private String title;

    @NotBlank
    @NotEmpty
    private String text;

    @NotNull
    @Size(min = 2)
    @ElementCollection
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    private List<Integer> answer;

    public Quiz() {}

    public Quiz(String creator, String title, String text,
                List<String> options,
                List<Integer> answer) {
        this.creator = creator;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
