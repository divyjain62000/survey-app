package com.survey.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.survey.domain.survey.Survey;
import com.survey.domain.survey.response.SurveyResponseTrack;
import com.survey.domain.survey.response.SurveyResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name="user")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 25)
    private String username;

    @Column(nullable = false,length = 25)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SurveyResponse> surveyResponseList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SurveyResponseTrack> surveyResponseTrackList;


    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Survey> surveyList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
