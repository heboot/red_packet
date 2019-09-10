package com.waw.hr.mutils.bean;

public class CheckWordBean {


    /**
     * id : 1
     * title : hello
     * test_translate : 你好--再见--明天见--喂
     * result : A--D
     * true_translate : 你好,喂
     */

    private int id;
    private String title;
    private String test_translate;
    private String result;
    private String true_translate;
    private int grade_every;
    private int elapsed_time;


    private String option;


    public int getGrade_every() {
        return grade_every;
    }

    public int getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(int elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public void setGrade_every(int grade_every) {
        this.grade_every = grade_every;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTest_translate() {
        return test_translate;
    }

    public void setTest_translate(String test_translate) {
        this.test_translate = test_translate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTrue_translate() {
        return true_translate;
    }

    public void setTrue_translate(String true_translate) {
        this.true_translate = true_translate;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
