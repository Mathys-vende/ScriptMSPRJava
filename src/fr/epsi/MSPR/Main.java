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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static String StaffPath = "src/fr/epsi/MSPR/staff.txt";
    private static String EquipementPath = "src/fr/epsi/MSPR/liste.txt";
    private static String OutPutDirectory = "src/fr/epsi/MSPR/htmlAgents/";
    private static String AgentsTxtDirectory = "src/epsi/MSPR/ListAgents/";

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
            String[] InfoSalarie = getContentTxtFile(AgentsTxtDirectory + agent + ".txt").toArray(new String[0]);
            File file = new File("src/fr/epsi/MSPR/template.html");
            try {
                Files.copy(file.toPath(),new File(OutPutDirectory+ agent+".html").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String nom = InfoSalarie[0]+" "+InfoSalarie[1];
            String mission = InfoSalarie[2];
            String mousqueton;
            if(Arrays.toString(InfoSalarie).contains("mousqueton")) mousqueton = "checked"; else mousqueton="";
            String gant;
            if(Arrays.toString(InfoSalarie).contains("gant")) gant = "checked"; else gant ="";
            String brassard;
            if(Arrays.toString(InfoSalarie).contains("brassard")) brassard = "checked"; else brassard = "";
            String menottes;
            if(Arrays.toString(InfoSalarie).contains("menotttes")) menottes = "checked"; else menottes = "";
            String cyno;
            if(Arrays.toString(InfoSalarie).contains("cyno")) cyno = "checked"; else cyno = "";
            String talky;
            if(Arrays.toString(InfoSalarie).contains("talky")) talky = "checked"; else talky = "";
            String lampe;
            if(Arrays.toString(InfoSalarie).contains("lampe")) lampe = "checked"; else lampe = "";
            String kit;
            if(Arrays.toString(InfoSalarie).contains("kit")) kit = "checked";else kit = "";
            String taser;
            if(Arrays.toString(InfoSalarie).contains("taser")) taser = "checked"; else taser = "";
            String lacrymo;
            if(Arrays.toString(InfoSalarie).contains("lacrymo")) lacrymo = "checked";else lacrymo = "";

            try {
                Path path = Paths.get(OutPutDirectory+ agent+".html");
                Stream<String> lines = Files.lines(path);
                List<String> replaced = lines.map(line -> line
                        .replaceAll("\\$nom", nom)
                        .replaceAll("\\$mission",mission)
                        .replaceAll("\\$mousqueton", mousqueton)
                        .replaceAll("\\$gant", gant)
                        .replaceAll("\\$brassard", brassard)
                        .replaceAll("\\$menottes", menottes)
                        .replaceAll("\\$cyno", cyno)
                        .replaceAll("\\$talky", talky)
                        .replaceAll("\\$lampe",lampe)
                        .replaceAll("\\$kit",kit)
                        .replaceAll("\\$taser",taser)
                        .replaceAll("\\$lacrymo",lacrymo)
                ).collect(Collectors.toList());
                Files.write(path, replaced);
                lines.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
