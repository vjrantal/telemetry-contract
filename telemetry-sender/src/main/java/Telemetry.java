import java.lang.Override;
import java.lang.String;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.0.1.
 */
public final class Telemetry extends Contract {
    private static final String BINARY = "6060604052341561000c57fe5b5b6103f48061001c6000396000f300606060405263ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663242a28da8114610045578063d4ad63521461009d575bfe5b341561004d57fe5b61009b600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965061014695505050505050565b005b34156100a557fe5b6100c673ffffffffffffffffffffffffffffffffffffffff60043516610259565b60408051602080825283518183015283519192839290830191850190808383821561010c575b80518252602083111561010c57601f1990920191602091820191016100ec565b505050905090810190601f1680156101385780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b73ffffffffffffffffffffffffffffffffffffffff3316600090815260208181526040909120825161017a92840190610316565b507f7b12042d6e5e2d07dfad9185bfc9ccb554e8d06b3a0e928584ef22439c3954753382604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018060200182810382528381815181526020019150805190602001908083836000831461021b575b80518252602083111561021b57601f1990920191602091820191016101fb565b505050905090810190601f1680156102475780820380516001836020036101000a031916815260200191505b50935050505060405180910390a15b50565b610261610395565b73ffffffffffffffffffffffffffffffffffffffff82166000908152602081815260409182902080548351601f6002600019610100600186161502019093169290920491820184900484028101840190945280845290918301828280156103095780601f106102de57610100808354040283529160200191610309565b820191906000526020600020905b8154815290600101906020018083116102ec57829003601f168201915b505050505090505b919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061035757805160ff1916838001178555610384565b82800160010185558215610384579182015b82811115610384578251825591602001919060010190610369565b5b506103919291506103a7565b5090565b60408051602081019091526000815290565b6103c591905b8082111561039157600081556001016103ad565b5090565b905600a165627a7a7230582053f5fd12baddb623238fe9d0bb2d87943fa5d960d4519bece5398f3f93cdb4610029";

    private Telemetry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Telemetry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<TelemetryReceivedEventResponse> getTelemetryReceivedEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("TelemetryReceived",
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        List<EventValues> valueList = extractEventParameters(event,transactionReceipt);
        ArrayList<TelemetryReceivedEventResponse> responses = new ArrayList<TelemetryReceivedEventResponse>(valueList.size());
        for(EventValues eventValues : valueList) {
            TelemetryReceivedEventResponse typedResponse = new TelemetryReceivedEventResponse();
            typedResponse.from = (Address)eventValues.getNonIndexedValues().get(0);
            typedResponse.telemetry = (Utf8String)eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TelemetryReceivedEventResponse> telemetryReceivedEventObservable() {
        final Event event = new Event("TelemetryReceived",
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, TelemetryReceivedEventResponse>() {
            @Override
            public TelemetryReceivedEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                TelemetryReceivedEventResponse typedResponse = new TelemetryReceivedEventResponse();
                typedResponse.from = (Address)eventValues.getNonIndexedValues().get(0);
                typedResponse.telemetry = (Utf8String)eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Future<TransactionReceipt> sendTelemetry(Utf8String telemetry) {
        Function function = new Function("sendTelemetry", Arrays.<Type>asList(telemetry), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<TransactionReceipt> getLatestTelemetry(Address addr) {
        Function function = new Function("getLatestTelemetry", Arrays.<Type>asList(addr), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public static Future<Telemetry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(Telemetry.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static Future<Telemetry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
        return deployAsync(Telemetry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialValue);
    }

    public static Telemetry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Telemetry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Telemetry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Telemetry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TelemetryReceivedEventResponse {
        public Address from;

        public Utf8String telemetry;
    }
}
