package com.survey.domain.survey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.survey.domain.question.Question;
import com.survey.domain.survey.response.SurveyResponse;
import com.survey.domain.survey.response.SurveyResponseTrack;
import com.survey.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table(name="survey")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime lastDateToFill;

    @Column(nullable = false)
    private boolean isResponseEditable=false;

    @ManyToOne
    private User createdBy;

    @JsonIgnore
    @OneToMany(mappedBy = "survey")
    private List<SurveyResponse> surveyResponseList;


    @JsonIgnore
    @OneToMany(mappedBy = "survey")
    private List<SurveyResponseTrack> surveyResponseTrackList;

    @JsonIgnore
    @OneToMany(mappedBy = "survey")
    private List<Question> questions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", lastDateToFill=" + lastDateToFill +
                ", isResponseEditable=" + isResponseEditable +
                ", questions=" + questions +
                '}';
    }
}
