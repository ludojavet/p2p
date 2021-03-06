package tools;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestsClient {

    public void uploadFilesData(String url, String fileId) throws Exception {
       DefaultHttpClient httpClient = new DefaultHttpClient();

        FileReading fr = new FileReading(fileId);


        url = "http://"+url+"/files/"+fileId;
        HttpPost postFileData = new HttpPost(url);

        String payload = "{\"content\":\""+fr.getData()+"\"}";
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

        postFileData.setEntity(entity);

        HttpResponse response = httpClient.execute(postFileData);

        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void uploadFilesMetadata(String url, Metadata metadata) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        url = "http://"+url+"/files";
        HttpPost postFileMetadata = new HttpPost(url);


        String payload = "{\"fileId\":\""+metadata.getFileId()+"\",\"size\":"+metadata.getSize()+",\"name\":\""+metadata.getName()+"\"}";
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

        postFileMetadata.setEntity(entity);

        HttpResponse response = httpClient.execute(postFileMetadata);

        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void registerPeers(String url, String myURL) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        url = "http://"+url+"/peers";
        HttpPost postPeer = new HttpPost(url);

        String payload = "{\"url\":\""+myURL+"\"}";
        StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);

        postPeer.setEntity(entity);

        HttpResponse response = httpClient.execute(postPeer);

        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void getFilesData(String url, String fileId) throws Exception { //Is OK
        DefaultHttpClient httpClient = new DefaultHttpClient();

        url = "http://"+url+"/files/"+fileId;
        HttpGet getFile = new HttpGet(url);

        HttpResponse response = httpClient.execute(getFile);

        BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

        String reader;
        String content="";
        while ((reader = br.readLine()) != null) {
            content+= reader;
        }

        //System.out.println(fileContent);
        System.out.println(response.getStatusLine().getStatusCode());

        br.close();
    }

    public HashMap<String, Metadata> getMetadata(String url) {


        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, Metadata> metadata = new HashMap<>();
        String result = restTemplate.getForObject("http://" +url + "/files", String.class);

        try {
            metadata = new ObjectMapper().readValue(result, new TypeReference<HashMap<String, Metadata>>(){});
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        System.out.println(metadata);

        return metadata;
    }

    public List<Peer> getListPeers(String url) {
        RestTemplate restTemplate = new RestTemplate();
        List<Peer> peers = new ArrayList<>();
        String result = restTemplate.getForObject("http://" +url + "/peers", String.class);


        try {
            peers = new ObjectMapper().readValue(result, new TypeReference<List<Peer>>(){});
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        System.out.println(peers.get(0).getUrl());

        return peers;
    }

    public void unregisterPeers(String url, String peerId) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        url = "http://"+url+"/peers/"+peerId;
        HttpDelete deletePeer = new HttpDelete(url);

        HttpResponse response = httpClient.execute(deletePeer);

        System.out.println(response.getStatusLine().getStatusCode());
    }

    public void deleteFilesData(String url, String fileId) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        url = "http://"+url+"/files/"+fileId;
        HttpDelete deleteFile = new HttpDelete(url);

        HttpResponse response = httpClient.execute(deleteFile);

        System.out.println(response.getStatusLine().getStatusCode());
    }

}