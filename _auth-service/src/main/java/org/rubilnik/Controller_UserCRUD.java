package org.rubilnik;

import org.rubilnik.users.User;

import org.hibernate.Session;
import org.hibernate.App.sessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManagerFactory;


class PostUserJsonReqBody{
    public User user;
}
class UserValidationInfo{
    public String id,password,email;
}
class DeleteUserJsonBody{
    public UserValidationInfo validation;
}
class PutUserJsonBody{
    public UserValidationInfo validation;
    public User user;
}
class PostUserVerificationJsonBody{
    public UserValidationInfo validation;
}
class postUserGetJsonBody{
    public UserValidationInfo validation;
}


@RestController
public class Controller_UserCRUD {

    
    @CrossOrigin("*")
    @PostMapping("/user")
    ResponseEntity<?> postUser(@RequestBody PostUserJsonReqBody body) throws Exception{
        // var body = objectMapper.readValue(jsonString, PostUserJsonReqBody.class);
        var user = new User(body.user.getName(),body.user.getEmail(),body.user.getPassword());

        var db_session = App.sessionFactory.openSession();
        var db_transaction = db_session.beginTransaction();
        db_session.persist(user);

        db_transaction.commit();
        db_session.close();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(new Object(){public String id = body.user.getId();});
    }


    @CrossOrigin("*")
    @DeleteMapping("/user")
    void deleteUser(@RequestBody DeleteUserJsonBody body) throws Exception{

        Session session = App.sessionFactory.openSession();

        Transaction transactionGet = session.beginTransaction();
        User user = session.get(User.class, body.validation.id);
        if (!user.getPassword().equals(body.validation.password)) throw new Exception("Invalid password for user");
        transactionGet.commit();

        Transaction transactionRemove = session.getTransaction();
        session.remove(user);
        transactionRemove.commit();

        session.close();
    }


    
    @CrossOrigin("*")
    @PutMapping("/user")
    void putUser(@RequestBody PutUserJsonBody body) throws Exception{

        var db_session = App.sessionFactory.openSession();
        var db_transaction = db_session.beginTransaction();
        var db_user = db_session.get(User.class, body.validation.id);
        if (!db_user.getPassword().equals(body.validation.password)) throw new Exception("invalid user password");
        db_user.setName(body.user.getName());
        db_user.setEmail(body.user.getEmail());
        db_user.setPassword(body.user.getPassword());
        
        db_session.merge(db_user);

        db_transaction.commit();
        db_session.close();
    }

    
    @CrossOrigin("*")
    @PostMapping("/user/verify")
    void postUserVerification(@RequestBody String jsonString) throws Exception {

        var body = objectMapper.readValue(jsonString, PostUserVerificationJsonBody.class);

        var db_session = App.sessionFactory.openSession();
        var query = db_session.createNativeQuery("select * from users where password='"+body.validation.password+"' and email='"+body.validation.email+"';", User.class);
        var db_user = query.uniqueResult();
        
        if (!db_user.getPassword().equals(body.validation.password)) throw new Exception("invalid user password");
        db_session.close();
    }

    
    @CrossOrigin("*")
    @PostMapping("/user/get")
    ResponseEntity<?> postUserGet(@RequestBody postUserGetJsonBody body) throws Exception {

        var db_session = App.sessionFactory.openSession();
        var db_transaction = db_session.beginTransaction();
        var db_query = db_session.createNativeQuery("select * from users where password='"+body.validation.password+"' and email='"+body.validation.email+"';", User.class);
        var db_user = db_query.uniqueResult();

        db_transaction.commit();
        db_session.close();

        return ResponseEntity.ok().body( new Object(){public User user = db_user;} );
    }
}