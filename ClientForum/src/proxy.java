import java.rmi.Remote;
import java.rmi.RemoteException;
// remote interface proxy
public interface proxy extends Remote
{
    public void ecouter(String msg) throws RemoteException;
}