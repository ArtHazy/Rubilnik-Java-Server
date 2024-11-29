package org.rubilnik.users;

import org.rubilnik.Quiz.Question;
import org.rubilnik.Quiz.Question.Choice;

public class Player extends User {
    public Player(User user){
        super(user);
    }
    
    public void choose(Question question, Choice choice) throws RuntimeException{
        checkRoomForNull();
        room.registerPlayerChoice(this, question, choice);
    }
}