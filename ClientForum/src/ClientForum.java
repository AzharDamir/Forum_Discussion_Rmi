import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientForum {

    public static void main(String[] args) {
        try {

            // R�cup�ration du stub du serveur
            Forum stub = (Forum) Naming.lookup("rmi://localhost:1099/Forum");

            UserImpl user = new UserImpl(stub);

            System.out.println("user id " + user.getId());

        } catch (Exception e) {
            System.err.println("Erreur Client : " + e.toString());
            e.printStackTrace();
        }
    }
}
