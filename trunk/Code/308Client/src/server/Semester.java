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
            return new Semester(d.getYear()+1900, 0);
        }
        return new Semester(d.getYear()+1901, 1);
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
            case 0:
                s+="FALL";
                break;
            case 1: s+="SPRING";
        }
        s+=""+year;
        return s;
    }
}
