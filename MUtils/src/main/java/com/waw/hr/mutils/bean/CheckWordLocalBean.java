package com.waw.hr.mutils.bean;

public class CheckWordLocalBean {

    private String option;

    private String title;

    private boolean correct;

    private boolean checked;

    private int grade_every;

    private int elapsed_time;

    public int getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(int elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public int getGrade_every() {
        return grade_every;
    }

    public void setGrade_every(int grade_every) {
        this.grade_every = grade_every;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
