package org.rubilnik;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.rubilnik.WebAPI.http.req.user_CRUD.UserValidationInfo;
import org.rubilnik.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;


class PostQuizReqJsonBody{
    public UserValidationInfo validation;
    public Quiz quiz;
}
class PutQuizReqJsonBody{
    public UserValidationInfo validation;
    public Quiz quiz;
}
class DeleteQuizReqJsonBody{
    public UserValidationInfo validation;
    public int id;
}

class PostQuizResJsonBody{
    public Quiz quiz;
}




@RestController
public class Controller_QuizCRUD {
    static ObjectMapper objectMapper = new ObjectMapper();
    static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    
    @CrossOrigin("*")
    @PostMapping("/quiz")
    ResponseEntity<?> postQuiz(@RequestBody PostQuizReqJsonBody body) throws Exception {
        // var body = objectMapper.readValue(jsonString, PostQuizReqJsonBody.class);
        
        var db_session = sessionFactory.openSession();
        var db_transaction = db_session.beginTransaction();
        var db_query = db_session.createNativeQuery("select * from users where "+"id="+"'"+body.validation.id+"'"+" and "+"password="+"'"+body.validation.password+"'", User.class);
        var db_user = db_query.uniqueResult();

        var quiz = db_user.createQuiz(body.quiz.getTitle(), body.quiz.getQuestions());
        // var res = new PostQuizResJsonBody(){ Quiz quiz = quiz_; };

        
        db_session.persist(quiz);
        db_transaction.commit();
        db_session.close();

        // return ResponseEntity.ok().body(objectMapper.writeValueAsString(quiz)); 
        return ResponseEntity.ok().body(quiz); 
    }


    
    @CrossOrigin("*")
    @PutMapping("/quiz")
    ResponseEntity<?> putQuiz(@RequestBody PutQuizReqJsonBody body) throws Exception {

        var db_session = sessionFactory.openSession();
        var db_transaction = db_session.beginTransaction();
        var db_quiz = db_session.get(Quiz.class, body.quiz.getId());
        if (!db_quiz.getAuthor().getId().equals(body.validation.id)) throw new Exception("Invalid quiz owner");

        body.quiz.setDateSaved(new Date());
        body.quiz.setAuthor(db_quiz.getAuthor());
        db_session.merge(body.quiz);

        db_transaction.commit();
        db_session.close();

        return ResponseEntity.ok().body(body.quiz); 
    }


    
    @CrossOrigin("*")
    @DeleteMapping("/quiz")
    void deleteQuiz(@RequestBody DeleteQuizReqJsonBody body) throws Exception {
        
        var session = sessionFactory.openSession();
        var transactionGet = session.beginTransaction();

        User user = session.get(User.class, body.validation.id);
        Quiz quiz = session.get(Quiz.class, body.id);
        if (!user.getPassword().equals(body.validation.password)) throw new Exception("Invalid password");
        if (!quiz.getAuthor().getId().equals(quiz.getAuthor().getId())) throw new Exception("Invalid quiz owner");
        session.remove(quiz);
        transactionGet.commit();

        session.close(); 
    }
}
