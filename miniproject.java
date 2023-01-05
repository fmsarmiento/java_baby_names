
/**
 * Write a description of miniproject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

public class miniproject {
    public void readOneFile(int year) {
    String fname = "data/yob" + year + ".txt";
    FileResource fr = new FileResource(fname);
    CSVParser parser = fr.getCSVParser(false); // false -> no header row. We access data by indexing
    for (CSVRecord rec : parser) {
        String name = rec.get(0);
        String gender = rec.get(1);
    }
    }
    
    public void printNames(FileResource fr) {
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
            System.out.println("Name "+rec.get(0) +
                               " Gender " + rec.get(1) +
                               " Num Born " + rec.get(2));
            }
        }
    }
    
    public void totalBirths(FileResource fr) {
        int totalBirthsM = 0;
        int totalBirthsF = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (rec.get(1).equals("M")) {
                totalBirthsM += numBorn;
                System.out.println("[M] name: "+rec.get(0));
            }
            else {
                totalBirthsF += numBorn;
                System.out.println("[F] name: "+rec.get(0));
            }
        }
        System.out.println("Total births (M): "+totalBirthsM);
        System.out.println("Total births (F): "+totalBirthsF);
    }
    
    public int getRank(int year, String name, String gender) {
        String fname = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        System.out.println("Opening file "+fname);
        int rank = -1;
        int csv_ctr = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
            csv_ctr += 1;
            if (rec.get(0).equals(name)) {
                rank = csv_ctr;
            }
        }
        }
        return rank;
    }
    
    public String getName(int year, int rank, String gender) {
        String fname = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        System.out.println("Opening file "+fname);
        String result = "NO NAME";
        int csv_ctr = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
            csv_ctr += 1;
            if (csv_ctr == rank) {
            result = rec.get(0);
            }
        }
        }
        return result;
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        String result="NO NAME";
        String newName = "";
        String fname = "data/yob" + year + ".csv";
        FileResource fr = new FileResource(fname);
        int name_rank = getRank(year,name,gender);
        fname = "data/yob" + newYear + ".csv";
        newName = getName(newYear, name_rank, gender);
        if (result.isEmpty() == false) {
            result = name + " born in " + year + " would be " + newName + " if born in " + newYear;
        }
        System.out.println(result);
    }
    
    public void yearOfHighestRank(String name, String gender) {
        String result = "";
        int lowest_rank = 99999999;
        double average_rank = 0;
        int number_files = 0;
        String year_rank = ""; //change to int?
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            int rank_ctr = 0;
            FileResource fr = new FileResource(f);
            for (CSVRecord rec : fr.getCSVParser(false)) {
                if (rec.get(1).equals(gender)) {
                rank_ctr += 1;
                if (rec.get(0).equals(name)) {
                    average_rank += rank_ctr;
                    number_files += 1;
                    if (rank_ctr < lowest_rank) {
                        lowest_rank = rank_ctr;
                        year_rank = f.getName();
                    }
                }
                }
            }
            
        }
        if (lowest_rank == 99999999) {
        lowest_rank = -1;
        }
        average_rank = average_rank / number_files;
        year_rank = year_rank.substring(3,7);
        result = name + " has an average rank of " + average_rank + ", with a highest rank on year " + year_rank;
        System.out.println(result);
    }
    
    public void getTotalBirthsRankedHigher(int year, String name, String gender) {
        int total_births = 0;
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if (rec.get(0).equals(name)) {
                    break;
                }
                else {
                    total_births += Integer.parseInt(rec.get(2));
                }
            }
        }
        System.out.println("Total births ranked higher from name "+name+" is "+total_births);
    }
    
    public void tester() {
    //FileResource fr = new FileResource();
    //printNames(fr);
    //totalBirths(fr);
    String name = "Ethan";
    String gender = "M";
    int rank = 155;
    int year = 2014;
    int rank_out;
    //int rank_out = getRank(year, name, gender);
    //System.out.println("Rank of "+name+": "+rank_out);
    //String name_out = getName(year, rank, gender);
    //System.out.println("Name of rank "+rank+": "+name);
    //int newYear = 1997;
    //whatIsNameInYear(name, year, newYear, gender);
    //yearOfHighestRank(name, gender);
    //getTotalBirthsRankedHigher(year, name, gender);
    rank_out = getRank(1960, "Emily", "F");
    System.out.println("Rank of Emily: "+rank_out);
    rank_out = getRank(1971, "Frank", "M");
    System.out.println("Rank of Frank: "+rank_out);
    String name_out = getName(1980, 350, "F");
    System.out.println("Name of rank 350: "+name_out);
    name_out = getName(1982, 450, "M");
    System.out.println("Name of rank 450: "+name_out);
    whatIsNameInYear("Susan", 1972, 2014, "F");
    whatIsNameInYear("Owen", 1974, 2014, "M");
    yearOfHighestRank("Genevieve", "F"); //1880 to 2014
    yearOfHighestRank("Mich", "M"); //1880 to 2014
    yearOfHighestRank("Susan", "F"); //1880 to 2014
    yearOfHighestRank("Robert", "M"); //1880 to 2014
    getTotalBirthsRankedHigher(1990, "Emily", "F");
    getTotalBirthsRankedHigher(1990, "Drew", "M");
    }
}
