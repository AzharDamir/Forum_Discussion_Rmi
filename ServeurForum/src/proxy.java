import java.rmi.*;
// remote interface proxy
public interface proxy extends Remote
{
    public void ecouter(String msg) throws RemoteException;
}