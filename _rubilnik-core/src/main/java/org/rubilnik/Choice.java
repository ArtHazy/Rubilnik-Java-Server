package org.rubilnik;
import java.util.List;
import java.util.stream.Collectors;

import org.rubilnik.Quiz.Question;
import org.rubilnik.users.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.LinkedList;

@Entity
    @Table(name = "choice")
    public class Choice {
        @Id
        @GeneratedValue
        private long id;
        @Column
        private String title;
        @Column
        private boolean isCorrect;
        @JsonBackReference
        @ManyToOne @JoinColumn
        private Question question;

        public Long getId() {
            return id;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public void setCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
        }
        protected Choice(){}
        Choice(String title, boolean isCorrect){
            this.title = title;
            this.isCorrect = isCorrect;
        }
        public boolean isCorrect() {
            return isCorrect;
        }
    }
