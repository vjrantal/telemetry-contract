import org.web3j.abi.datatypes.Utf8String;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;


public class TelemetrySender {
    private static final String RPC_URL = "http://localhost:22000";
    private static final String CONTRACT_ADDRESS = "0xcaf61ea0a20e648bf43f1d546cf8a315394baf9b";
    private static final String FROM_ADDRESS = "0xed9d02e382b34818e88b88a309c7fe71e65f419d";
    private static final String PRIVATE_FOR = "ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=";

    private static final Logger LOGGER = Logger.getLogger(TelemetrySender.class.getName());
    private Quorum web3j = null;

    public boolean init() {
        try {
            this.web3j = Quorum.build(new HttpService(RPC_URL));
        } catch (Throwable ex) {
            LOGGER.log(Level.WARNING, "Error", ex);
        }
        if (this.web3j == null) {
            LOGGER.info("Web3j not initialized");
            return false;
        }
        return true;
    }

    public boolean run() {
        int randomValue = ThreadLocalRandom.current().nextInt(100, 10000);
        String now = Instant.now().toString();
        try {
            String telemetry = "{\"deviceid\":\"dummy\",\"property\":\"InstantaneousDemand\",\"timezone\":\"UTC\",\"unit\":\"w\",\"values\":[{\"starttime\":\"" + now + "\",\"value\":\"" + randomValue + "\"}]}";
            ClientTransactionManager transactionManager = new ClientTransactionManager(
                    this.web3j, FROM_ADDRESS,
                    Arrays.asList(PRIVATE_FOR)
            );
            Telemetry telemetryContract = Telemetry.load(
                    CONTRACT_ADDRESS,
                    this.web3j, transactionManager,
                    new BigInteger("0"),
                    new BigInteger("1000000")
            );
            TransactionReceipt receipt = telemetryContract.sendTelemetry(new Utf8String(telemetry)).get();
            LOGGER.info("Transaction hash: " + receipt.getTransactionHash());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TelemetrySender telemetrySender = new TelemetrySender();
        boolean success = true;
        success = telemetrySender.init();
        while (success) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            success = telemetrySender.run();
        }
    }

}
