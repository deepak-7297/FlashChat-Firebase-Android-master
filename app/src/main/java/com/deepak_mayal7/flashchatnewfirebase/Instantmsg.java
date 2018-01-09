package com.deepak_mayal7.flashchatnewfirebase;

/**
 * Created by Pooja on 08-01-2018.
 */

public class Instantmsg {

    private String message;
    private String Author;
    public Instantmsg(String message, String author) {
        this.message = message;
        Author = author;
    }


    public Instantmsg() {
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return Author;
    }
}
