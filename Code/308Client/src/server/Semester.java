/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author TJ
 */
public class Semester implements Comparable, Serializable{
    public static final int FALL = 16;
    public static final int SPRING = 2;
    public static final int WINTER = 1;
    public static final int SUMMER1 = 4;
    public static final int SUMMER2 = 8;
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
    public int getYear(){return year;}
    public int getSeason() {return season;}
    public Semester nextSemester() {
        int season=this.season,year=this.year;
        season=(season==FALL?SPRING:FALL);
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
    private static int log2(int num) {
        int ans=0;
        while(num>1) {
            num=num/2;
            ans++;
        }
        return ans;
    }
    @Override
    public int compareTo(Object o) {
       Semester s=(Semester)o;
        return 10*(year-s.getYear())+(log2(season)-log2(s.getSeason()));
    }
}
