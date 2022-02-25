package pl.projekty.springexternalapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hello my friend!";
    }

    @GetMapping(value = "/helloToClient")
    private String getHelloToClient(){
        String uri = "http://localhost:8080/hello";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    @GetMapping(value = "/countries")
    public void getCountries(){

        FileWriter file = null;
        
        String url = "https://restcountries.com/v3.1/all";

        RestTemplate restTemplate = new RestTemplate();
        Object[] countries = restTemplate.getForObject(url, Object[].class);
        List<Object> mycountriesList = Arrays.asList(countries);

        JSONObject obj = new JSONObject();
        obj.put("Countries List", mycountriesList);

        try {
            file = new FileWriter("krajeApi.txt");
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
