package cloud.cinder.ethereumnodemonitor.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Health
@ApplicationScoped
public class EthereumSyncingHealthCheck implements HealthCheck {

    private Web3j web3j;

    public EthereumSyncingHealthCheck() {
    }

    @Inject
    public EthereumSyncingHealthCheck(final Web3j web3j) {
        this.web3j = web3j;
    }

    @Override
    public HealthCheckResponse call() {
        final HealthCheckResponseBuilder builder = HealthCheckResponse
                .named("Ethereum Sync Health");
        return getLatestBlock()
                .map(block -> {
                    final Date date = new Date(block.getBlock().getTimestamp().longValue() * 1000);
                    final LocalDateTime blockTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                    if (blockTime.plus(10, ChronoUnit.MINUTES).isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
                        return builder.down()
                                      .withData("error", "last block is older than 30 minutes.(" + blockTime.toString() + ", block: " + block.getBlock().getNumber() + ")");

                    } else {
                        return builder.up().withData("message", "latest block is " + block.getBlock().getNumber());
                    }
                }).orElseGet(builder::down)
                .build();
    }

    private Optional<EthBlock> getLatestBlock() {
        try {
            return Optional.ofNullable(web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).flowable().blockingFirst());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
