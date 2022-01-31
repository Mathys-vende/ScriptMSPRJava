package fr.epsi.MSPR;

import fr.epsi.MSPR.models.Equipement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static String StaffPath = "src/fr/epsi/MSPR/staff.txt";
    private static String EquipementPath = "src/fr/epsi/MSPR/liste.txt";

    public static void main(String[] args) {
        ArrayList<String> ListStaff = ListStaff();
        ArrayList<Equipement> ListEquipement = ListEquipement();
        CreateHtmlFile(ListStaff);
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
    public static void CreateHtmlFile(ArrayList<String> listAgents){
        for (String agent: listAgents) {
            ArrayList<String> InfoSalarie = getContentTxtFile("src/fr/epsi/MSPR/ListAgents/"+agent+".txt");
            File file = new File("src/fr/epsi/MSPR/template.html");
            try {
                Files.copy(file.toPath(),new File("src/fr/epsi/MSPR/htmlAgents/"+ agent+".html").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String nom = InfoSalarie.get(0)+" "+InfoSalarie.get(1);
            try {
                Path path = Paths.get("src/fr/epsi/MSPR/htmlAgents/"+ agent+".html");
                Stream<String> lines = Files.lines(path);
                List<String> replaced = lines.map(line -> line.replaceAll("nom", nom).replaceAll("test",InfoSalarie.get(2))).collect(Collectors.toList());
                Files.write(path, replaced);
                lines.close();
                System.out.println("Find and Replace done!!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
