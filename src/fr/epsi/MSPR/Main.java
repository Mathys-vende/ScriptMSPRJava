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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private final static String StaffPath = "../ressources/staff.txt";
    private final static String EquipementPath = "../ressources/liste.txt";
    private final static String OutPutDirectory = "../ressources/output/";
    private final static String AgentsTxtDirectory = "../ressources/ListAgents/";
    private final static String AgentsPhotoDirectory = "../ressources/ListPhotosAgents/";

    public static void main(String[] args) throws IOException {
        ArrayList<String> ListStaffUncheck = ListStaff();
        String[] ListPhoto = ListPhotos();
        ArrayList<String> ListStaff = CheckIfStaffHasPhoto(ListStaffUncheck, ListPhoto);
        ArrayList<String> ListEquipement = ListEquipement();


        CreateHtmlFile(ListStaff, ListEquipement);
    }

    public static ArrayList<String> CheckIfStaffHasPhoto(ArrayList<String> ListStaff, String[] photoStaff)
    {
        ArrayList<String> photoName = new ArrayList<>();
        for(String photo : photoStaff){
            String[] photoSplit = photo.split("\\.");
            photoName.add(photoSplit[0]);
        }
        for(Iterator<String> itr = ListStaff.iterator();
        itr.hasNext();){
            String agent = itr.next();
            String[] agentSplit = agent.split("\\.");
            if(!photoName.contains(agentSplit[0])){
                itr.remove();
            }else{
                File file = new File(AgentsPhotoDirectory+agent+".jpg");
                try {
                    Files.copy(file.toPath(),new File(OutPutDirectory+"photos/"+agent+".jpg").toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ListStaff;
    }
    public static String[] ListPhotos(){
        File f = new File(AgentsPhotoDirectory);
        return f.list();
    }
    public static ArrayList<String> ListEquipement(){
        ArrayList<String> ListEquipementString = getContentTxtFile(EquipementPath);
        ArrayList<Equipement> ListEquipement = new ArrayList<Equipement>();
        ArrayList<String> ListEquip = new ArrayList<>();
        for(String line: ListEquipementString){
            String[] items = line.split("\t");
            Equipement equipement =  new Equipement(items[0], items[1]);
            ListEquipement.add(equipement);
            ListEquip.add(items[0]);
        }
        return ListEquip;
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
    public static void CreateHtmlFile(ArrayList<String> listAgents, ArrayList<String> listEquipements){
        for (String agent: listAgents) {
            String[] InfoSalarie = getContentTxtFile(AgentsTxtDirectory + agent + ".txt").toArray(new String[0]);
            File file = new File("../ressources/template.html");
            try {
                Files.copy(file.toPath(),new File(OutPutDirectory+"agents/"+ agent+".html").toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String nom = InfoSalarie[0]+" "+InfoSalarie[1];
            String mission = InfoSalarie[2];
            String photo = "../photos/"+agent+".jpg";
            String Equipements = "";
            for( String equipement : listEquipements){
                Equipements = Equipements.concat(
                        "<div class=\"col-md-6 col-lg-4 mb-5\">\n" +
                        "        <div class=\"mx-auto\">\n" +
                        "          <div class=\"card text-center\">\n" +
                        "            <div class=\"card-header\">\n" +
                        "            </div>\n" +
                        "            <div class=\"card-body\"  id=\"card-body\">\n" +
                        "              <h5 class=\"card-title\">"+equipement+"</h5>\n" +
                        "            </div>\n" +
                        "            <div class=\"card-footer\">\n" +
                        "            </div>\n" +
                        "          </div>\n" +
                        "        </div>\n" +
                        "      </div>");
            }
            try {
                Path path = Paths.get(OutPutDirectory+"agents/"+ agent+".html");
                Stream<String> lines = Files.lines(path);
                String finalEquipements = Equipements;
                List<String> replaced = lines.map(line -> line
                        .replaceAll("\\$photo", photo)
                        .replaceAll("\\$nom", nom)
                        .replaceAll("\\$mission",mission)
                        .replaceAll("\\$objet", finalEquipements)
                ).collect(Collectors.toList());
                Files.write(path, replaced);
                lines.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
