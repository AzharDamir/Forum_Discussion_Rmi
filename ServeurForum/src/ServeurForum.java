import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServeurForum {

    public static void main(String args[]) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);

            ForumImpl forum = new ForumImpl();

            Naming.rebind("rmi://localhost:1099/Forum",forum);

            System.out.println("Serveur pret.");
        } catch (Exception e) {
            System.err.println("Erreur Serveur : " + e.toString());
            e.printStackTrace();
        }
    }
}
