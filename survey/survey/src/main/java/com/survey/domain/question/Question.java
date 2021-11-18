package com.survey.domain.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.survey.domain.survey.response.SurveyResponse;
import com.survey.domain.survey.Survey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name="question")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String que;

    @ManyToOne
    private Survey survey;

    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<SurveyResponse> surveyResponseList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", que='" + que + '\'' +
                ", survey=" + survey +
                ", surveyResponseList=" + surveyResponseList +
                '}';
    }
}
