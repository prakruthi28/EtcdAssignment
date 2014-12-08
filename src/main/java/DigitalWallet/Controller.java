import java.util.LinkedList;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.justinsb.etcd.EtcdResult;
import com.justinsb.etcd.*;

@RestController
public class Controller {

        EtcdClient client;

        Controller(){
        try {
                client = new EtcdClient(URI.create("http://54.67.104.154:4001/"));
                        //this.client.set("counter", "0");
        } catch (Exception e) {

        }
}

        @RequestMapping(value = "/api/v1/counter",method = RequestMethod.GET)
        public @ResponseBody Integer updateCounter() throws Exception{
                String key = "009266452";
                try {
                        EtcdResult result = this.client.get(key);
                        Integer counterResult = Integer.parseInt(result.node.value);
                        counterResult++;
                        result = this.client.set(key, ""+counterResult);
                        return counterResult;
                }
                catch (Exception e) {
                        this.client.set(key, "1");
                        return new Integer(1);
                }
         }

        @RequestMapping(value="/api/v1/reset", method = RequestMethod.GET)
        public @ResponseBody Integer resetCounter() throws Exception{
                String key = "009266452";
                EtcdResult result = this.client.set(key, "0");
                Integer i = 0;
                return i;
        }
}
