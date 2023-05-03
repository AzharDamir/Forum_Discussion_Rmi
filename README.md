# Forum_Discussion_Rmi
Mini Projet
L'objectif de ce mini projet est la gestion d'un Forum de discussion pouvant faire intervenir un 
nombre quelconque d'intervenants utilisant une application User. Chaque User peut émettre 
des messages qui sont diffusés à l'ensemble des User. Les messages ne sont pas mémorisés 
par le Forum. Seuls les User présents sur le Forum reçoivent les messages émis. Pour émettre, 
un User doit être connecté au Forum.
L'interface du Forum est la suivante :
 entrer : permet à un User de se connecter au Forum.
 quiter : permet à un User de se déconnecter du Forum.
 dire : permet à un User d'émettre un message.
 qui : permet à un User de connaître l'ensemble des intervenants connectés au Forum.
La description exacte de ces méthodes est donnée dans Forum.java (voir ci-dessous).
Pour pouvoir diffuser les messages, le Forum doit pouvoir rappeler les User et leur donner les 
messages à afficher. Il le fait par le biais d'un objet Proxy, dont l'User donne une référence 
au Forum lorsqu'il se connecte.
Utilisation de RMI
L’application est composée des fichiers suivants :
 Forum.java ForumImpl.java : il s'agit du serveur Forum. Il fournit les méthodes 
appelables à distance par les User (entrer, quiter, qui, dire). Le Forum est client des 
objets proxy permettant de diffuser les messages.
import java.rmi.*;
// interface of the forum server
public interface Forum extends Remote {
public int entrer(proxy pr) throws RemoteException;
public void dire(int id, String msg) throws RemoteException;
public String qui() throws RemoteException;
public void quiter(int id) throws RemoteException;
}
 User.java UserImpl.java : il s'agit de la partie cliente de l'application. Elle inclut une 
interface graphique et les actions (ActionListener) déclenchées lorsque l'on utilise 
l'interface graphique. Elle fournit également la méthode ecrire permettant d'afficher un 
message dans l'interface graphique. 

// interface User
public interface User {
 public void ecrire(String msg);
}
 proxy.java proxyImpl.java : il s'agit de l'objet accessible à distance permettant au 
serveur Forum de rappeler les clients User pour diffuser un message (et le faire 
afficher chez ces clients).
