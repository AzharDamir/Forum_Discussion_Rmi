import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ProxyImpl extends UnicastRemoteObject implements proxy {
    private User user;

    public ProxyImpl(User user) throws RemoteException {
        this.user = user;
    }

    public void ecouter(String msg) throws RemoteException {
        user.ecrire(msg);
    }
}
