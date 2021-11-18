package com.survey.repository.question;

import com.survey.domain.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer>{
    List<Question> findAllBySurveyId(Integer surveyId);
}
