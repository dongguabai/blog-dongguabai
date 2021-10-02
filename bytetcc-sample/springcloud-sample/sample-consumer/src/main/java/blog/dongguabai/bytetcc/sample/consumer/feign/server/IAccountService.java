package blog.dongguabai.bytetcc.sample.consumer.feign.server;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 00:49
 */
@FeignClient(value = "SPRINGCLOUD-SAMPLE-PROVIDER")
public interface IAccountService {

    @RequestMapping(method = RequestMethod.POST, value = "/increase")
    public void increaseAmount(@RequestParam("acctId") String accountId, @RequestParam("amount") double amount);

    @RequestMapping(method = RequestMethod.POST, value = "/decrease")
    public void decreaseAmount(@RequestParam("acctId") String accountId, @RequestParam("amount") double amount);

}
