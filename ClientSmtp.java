import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientSmtp {
    private String serveurSmtp;
    private int port;
    private String hostname;
    private String from;
    private String to;
    private String subject;
    private String body;


    public ClientSmtp(String serveurSmtp, int port, String hostname){
        this.serveurSmtp = serveurSmtp;
        this.port = port;
        this.hostname = hostname ;

    }

    public boolean sendMail(String from, String to, String subject, String body) {
        try {
            boolean rep;

            Socket client = new Socket(serveurSmtp, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            out.write("EHLO " + hostname + "\n");
            out.flush();

            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            out.write("MAIL FROM: "+ from +"\n");
            out.flush();

            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            out.write("RCPT TO: "+to + "\n");
            out.flush();

            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            out.write("DATA"+"\n");
            out.flush();
            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            out.write("FROM: "+from +"\n");
            out.write("TO: "+to+"\n");
            out.write("SUBJECT: "+subject+"\n");
            out.write(body+"\n");
            out.write(".\n");
            out.flush();
            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            out.write("QUIT \n");

            out.close();
            client.close();

        }catch (UnknownHostException e) {
            e.printStackTrace();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    public boolean analyserReponseServeur(BufferedReader in) throws IOException {

        String reponseSeveur;

        while ((reponseSeveur = in.readLine()) != null) {

            if (reponseSeveur.charAt(3) != '-')
                break;
        }

        if (reponseSeveur.charAt(0) == '4' || reponseSeveur.charAt(0) == '5') {
            System.out.println(reponseSeveur);
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        ClientSmtp clientSmtp = new ClientSmtp("139.124.187.23",25,"pluton.aix.univ-amu.fr");

        if ((clientSmtp.sendMail("macron","girard","minecraft","j'ai tout brulé"))){
            System.out.printf("ok");
        }

    }

}