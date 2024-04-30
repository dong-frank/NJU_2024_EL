package org.example;

/**
 * 用户给出猜测结果(地名形式)
 */
public class User {
    private String guess_name;
    private int guess_no;

    public User(String guess_name){
        this.guess_name=guess_name;
        //TODO:根据地点名称对应编号
        //this.guess_no=
    }

    public int getGuess_no(){
        return this.guess_no;
    }
}
