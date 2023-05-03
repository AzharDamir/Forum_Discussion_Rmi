import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForumImpl extends UnicastRemoteObject implements Forum {
    private ArrayList<proxy> users;

    public ForumImpl() throws RemoteException {
        users = new ArrayList<proxy>();
    }

    public int entrer(proxy pr) throws RemoteException {
        int id = users.size();
        users.add(pr);

        this.dire(id, "Bonjour tout le monde !");
        return id;
    }

    public void dire(int id, String msg) throws RemoteException {
        if(users.get(id) != null) {
            for (proxy pr : users) {
                int nvid=id+1;
                pr.ecouter("User " + nvid+ ": " + msg);
            }
        }
    }

    public String qui() throws RemoteException {
        String whichUsers = new String();
        for (int i = 0; i < users.size(); i++) {
            int nvid=i+1;
            whichUsers += "User " +nvid+ "\n";
        }
        return "Les utilisateurs qui sont connect�s sont:\n"+whichUsers;
    }

    public void quiter(int id) throws RemoteException {
        users.remove(id);
    }
}
