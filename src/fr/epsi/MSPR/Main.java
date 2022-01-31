package fr.epsi.MSPR;

import fr.epsi.MSPR.models.Equipement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static String StaffPath = "src/fr/epsi/MSPR/staff.txt";
    private static String EquipementPath = "src/fr/epsi/MSPR/liste.txt";

    public static void main(String[] args) {
        ArrayList<String> ListStaff = ListStaff();
        ArrayList<Equipement> ListEquipement = ListEquipement();
    }

    public static ArrayList<Equipement> ListEquipement(){
        ArrayList<String> ListEquipementString = getContentTxtFile(EquipementPath);
        ArrayList<Equipement> ListEquipement = new ArrayList<Equipement>();
        for(String line: ListEquipementString){
            String[] items = line.split("\t");
            Equipement equipement =  new Equipement(items[0], items[1]);
            ListEquipement.add(equipement);
        }
        return ListEquipement;
    }
    public static ArrayList<String> ListStaff(){
        ArrayList<String> StaffList = getContentTxtFile(StaffPath);
        return StaffList;
    }

    public static ArrayList<String> getContentTxtFile(String Path){
        ArrayList<String> content = new ArrayList<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(Path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                content.add(line);
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
