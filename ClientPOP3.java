import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientPOP3 {

    private String adresse;
    private int port;
    private String user;
    private String pass;
    private int msg;

    public ClientPOP3(String adresse,int port,String user,String pass,int msg){
        this.adresse = adresse;
        this.msg=msg;
        this.pass = pass;
        this.port = port;
        this.user = user;

    }

    public boolean retrMail(){
        Socket client = null;
        boolean rep;

        try {
            client = new Socket(adresse, port);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            out.write("user " + user + "\n");
            out.flush();
            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;
            out.write("pass " + pass + "\n");
            out.flush();
            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            out.write("retr " + msg + "\n");
            out.flush();
            rep = analyserReponseServeur(in);
            if(rep == false)
                return false;

            while (!(in.readLine().equals("."))){
                System.out.println(in.readLine());
            }

            out.write("quit \n");
            out.close();
            client.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean analyserReponseServeur(BufferedReader in) throws IOException {

        String reponseSeveur;

        reponseSeveur = in.readLine();

        if (reponseSeveur.charAt(0) != '+') {
            System.out.println(reponseSeveur);
            return false;
        }
        return true;
    }



    public static void main(String[] args){
        ClientPOP3 clientPOP3 = new ClientPOP3("139.124.187.23",110,"fievet","fievet",150);

        clientPOP3.retrMail();

    }

}
