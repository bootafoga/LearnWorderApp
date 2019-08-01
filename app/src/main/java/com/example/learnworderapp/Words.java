package com.example.learnworderapp;

public class Words {
    private String rus;
    private String eng;

    public static final Words[] words = {
            new Words("Собака", "Dog"),
            new Words("Кошка", "Cat"),
            new Words("Дом", "House"),
            new Words("Мышь", "Mouse"),
            new Words("Машина", "Car"),
            new Words("Солнце", "Sun"),
            new Words("Случайно", "Accidentally"),
            new Words("Выше", "Above")
    };

    private Words(String rus, String eng){
        this.rus = rus;
        this.eng = eng;
    }

    public String getRus() {
        return rus;
    }

    public String getEng() {
        return eng;
    }
}
