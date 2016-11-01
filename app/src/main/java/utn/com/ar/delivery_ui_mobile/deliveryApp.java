package utn.com.ar.delivery_ui_mobile;

import android.app.Application;

import java.util.HashSet;
import java.util.Set;

import utn.com.ar.delivery_ui_mobile.domain.Usuario;

public class deliveryApp extends Application {

    private static Set<Usuario> usuarios = new HashSet<Usuario>();
    private static Usuario loggedUser;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        usuarios = new HashSet<Usuario>();
//        loggedUser = null;
//    }

    public Usuario getUsuarioLogeado(String userName) {
        for (Usuario user : usuarios) {
            if (user.getUser().equals(userName))
                return user;
        }
        return null;
    }

    public void addUsuario(Usuario usuarioRegistrado) throws Exception {
        for (Usuario user : usuarios) {
            if (user.getUser().equals(usuarioRegistrado.getUser())) {
                throw new Exception("Usuario Ya existe");
            }
        }
        usuarios.add(usuarioRegistrado);
        loggedUser = usuarioRegistrado;
    }

    public void setLoggedUser(Usuario loggedUser) {
        deliveryApp.loggedUser = loggedUser;
    }

    public static Usuario getLoggedUser() {
        return loggedUser;
    }
}
