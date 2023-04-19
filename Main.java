import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        try{
            String fileName=args[0] ;
            if(!fileName.endsWith(".arxml")){
                throw new NotValidExtension("Invalid file extenision");
            }
            File file =new File(fileName) ;
            FileInputStream inputStream= new FileInputStream(file) ;
            int d ;
            StringBuilder stringBuilder=new StringBuilder();
            while((d=inputStream.read())!=-1){
                stringBuilder.append((char)d) ;
            }
            String data=stringBuilder.toString();
            Scanner scanner = new Scanner(data) ;
            ArrayList<Div>divs=new ArrayList<>();
            int flag=0 ;
            while(scanner.hasNextLine()){
                flag=1 ;
                String line =scanner.nextLine() ;
                if(line.contains("<CONTAINER")){
                    String divId=line.substring(line.indexOf("ID="),line.indexOf(">")) ;
                    String short_Name=scanner.nextLine();
                    String s =short_Name.substring(short_Name.indexOf(">")+1,short_Name.indexOf("</")) ;
                    String long_Name =scanner.nextLine() ;
                    String l =long_Name.substring(long_Name.indexOf(">")+1,long_Name.indexOf("</")) ;
                    Div div = new Div() ;
                    div.setDivID(divId);
                    div.setSHORT_NAME(s);
                    div.setLONG_NAME(l);
                    divs.add(div) ;
                }
            }
            if (flag==0){
                throw new EmptyAutosarFileException("It is not allowed to pass empty file ") ;
            }
            Collections.sort(divs);
            String outName=fileName.substring(0, fileName.indexOf(".")  )+"_mod.arxml";
            FileOutputStream outputStream =new FileOutputStream(outName) ;
            outputStream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
            outputStream.write( "<AUTOSAR>\n".getBytes() );
            for(int i=0 ; i<divs.size() ;i++){
                outputStream.write(divs.get(i).toString().getBytes());
            }
            outputStream.write( "</AUTOSAR>\n".getBytes() );

        } catch (NotValidExtension e) {
            System.out.println("");
        } catch (FileNotFoundException e) {
            e=new FileNotFoundException("File Not Found!");
        } catch (IOException e) {
            e= new IOException("IO Exception!") ;
        }catch(EmptyAutosarFileException e){
            System.out.println("");
        }

    }
}
class Div implements Comparable<Div>{
    public static void main(String[] args) {


    }
    private String DivID ;
    private String  SHORT_NAME;
    private String  LONG_NAME;
    public String getDivID(){
        return DivID;
    }
    public void setDivID(String divID){
        DivID=divID;
    }
    public String getSHORT_NAME(){
        return SHORT_NAME;
    }
    public void setSHORT_NAME(String SHORT_NAME){
        this.SHORT_NAME=SHORT_NAME;
    }
    public String getLONG_NAME(){
        return LONG_NAME;
    }
    public void setLONG_NAME(String LONG_NAME){
        this.LONG_NAME=LONG_NAME;
    }
    public Div(){}
    @Override
    public String toString(){
        return "    <CONTAINER UUID="+this.getDivID()+">\n"
                +"      <SHORT-NAME>"+this.getSHORT_NAME()+"</SHORT-NAME>\n"
                +"      <LONG-NAME>"+this.getLONG_NAME()+"<LONG-NAME>\n"
                +"  </CONTAINER>\n";
    }
    @Override
    public int compareTo(Div o){
        if(this.getSHORT_NAME().charAt(9)>o.getSHORT_NAME().charAt(9)){
            return 1 ;
        }else if (this.getSHORT_NAME().charAt(9) < o.getSHORT_NAME().charAt(9)){
            return -1 ;
        }else{
            return 0 ;
        }

    }


}
class  NotValidExtension extends Exception{
    public NotValidExtension(String message){
        System.out.println(message);
    }
}
class EmptyAutosarFileException extends RuntimeException{
    public EmptyAutosarFileException(String message){
        System.out.println(message);
    }
}