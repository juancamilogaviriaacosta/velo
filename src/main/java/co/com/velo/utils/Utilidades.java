package co.com.velo.utils;

import java.util.List;

/**
 * @author juan
 */
public class Utilidades {
    
    public static String toJsonArrEntity(List obj) {
        String resultado = "[";
        if (obj != null) {
            for (int i = 0; i < obj.size(); i++) {
                Object object = obj.get(i);
                resultado = resultado + object.toString();
                if ((i + 1) != obj.size()) {
                    resultado = resultado + ",";
                }
            }
        }
        resultado = resultado + "]";
        return resultado;
    }

}
