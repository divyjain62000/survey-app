package com.survey.domain.survey.response;

import com.survey.domain.survey.Survey;
import com.survey.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="survey_response")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class SurveyResponseTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Survey survey;

}
