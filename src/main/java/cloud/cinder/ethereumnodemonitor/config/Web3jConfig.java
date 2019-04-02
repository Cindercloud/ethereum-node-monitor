package cloud.cinder.ethereumnodemonitor.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class Web3jConfig {

    private static final Logger log = Logger.getLogger(Web3jConfig.class);

    @ConfigProperty(name = "ethereum.endpoint")
    public String ethereumEndpoint;

    @Produces
    public Web3j constructWeb3j() {
        log.info("starting up web3j with ethereum endpoint: " + ethereumEndpoint);
        return Web3j.build(new HttpService(ethereumEndpoint));
    }
}
