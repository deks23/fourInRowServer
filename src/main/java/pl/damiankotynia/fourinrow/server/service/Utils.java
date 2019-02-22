package pl.damiankotynia.fourinrow.server.service;

public class Utils {
    public static boolean isEmpty(Object object){
        if(object==null){
            return true;
        }
        return false;
    }

 /*   public boolean serviceNotEmpty(Service service){
        if(isBlank(service.getCustomerName())&&service.getStartTime()!=null)
            return false;

    }*/

    public boolean isBlank(String str){
        if(str==null)
            return false;
        str.replace("\\s", "");
        if ("".equals(str))
            return false;
        return true;
    }

    public static final String INBOUND_CONNECTION_LOGGER = "I_CONNECTION \t\t >>>> ";
    public static final String OUTBOUND_CONNECTION_LOGGER = "O_CONNECTION \t\t >>>> ";
}
