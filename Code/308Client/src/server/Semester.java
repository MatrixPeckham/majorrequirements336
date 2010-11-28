/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.Date;

/**
 *
 * @author TJ
 */
public class Semester {
    public static final int FALL = 1;
    public static final int SPRING = 2;
    public static final int WINTER = 4;
    public static final int SUMMER1 = 8;
    public static final int SUMMER2 = 16;
    private int year;
    private int season;
    Semester(int year, int season) {
        this.year=year;
        this.season=season;
    }
    public static Semester freeSemester() {
        Date d=new Date();
        int month=d.getMonth();
        if(month>2 && month<9) {
            return new Semester(d.getYear()+1900, FALL);
        }
        return new Semester(d.getYear()+1901, SPRING);
    }
    public int getSeason() {return season;}
    public Semester nextSemester() {
        int season=this.season,year=this.year;
        season=(season+1)%2;
        if(season==1) {
            year++;
        }
        return new Semester(year,season);
    }
    public String toString() {
        String s="";
        switch(season) {
            case FALL:
                s+="FALL";
                break;
            case SPRING:
                s+="SPRING";
                break;
            case WINTER:
                s+="WINTER";
                break;
            case SUMMER1:
                s+="SUMMER1";
                break;
            case SUMMER2:
                s+="SUMMER2";
                break;
        }
        s+=" "+year;
        return s;
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Semester){
            Semester s = (Semester)o;
            return s.season==season&&s.year==year;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.year;
        hash = 29 * hash + this.season;
        return hash;
    }
}
