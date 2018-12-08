package tools;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception{
        RequestsClient ctrl = new RequestsClient();
        //ctrl.getFilesData("192.168.43.252:8080","f1.txt");
        //ctrl.uploadFilesMetadata("192.168.43.253:8080","f1.txt");
        //ctrl.getMetadata("192.168.43.253:8080");
        ctrl.uploadFilesData("192.168.43.253:8080","f1.txt","102488948");
        //ctrl.registerPeers("192.168.43.252:8080","192.168.43.253:8080");
        //ctrl.listPeers("192.168.43.252:8080");
        //ctrl.unregisterPeers("192.168.43.252:8080","192.168.43.253:8080");
        //ctrl.deleteFilesData("192.168.43.252:8080","f1.txt");
    }
}