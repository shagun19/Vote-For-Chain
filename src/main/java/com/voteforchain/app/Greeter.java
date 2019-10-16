package com.voteforchain.app;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class Greeter extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506103d5806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806342cbb15c14610051578063a41368621461006b578063cfae321714610113578063e3b39c2c14610190575b600080fd5b610059610236565b60408051918252519081900360200190f35b6101116004803603602081101561008157600080fd5b81019060208101813564010000000081111561009c57600080fd5b8201836020820111156100ae57600080fd5b803590602001918460018302840111640100000000831117156100d057600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061023b945050505050565b005b61011b610252565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561015557818101518382015260200161013d565b50505050905090810190601f1680156101825780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610111600480360360208110156101a657600080fd5b8101906020810181356401000000008111156101c157600080fd5b8201836020820111156101d357600080fd5b803590602001918460018302840111640100000000831117156101f557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506102e7945050505050565b435b90565b805161024e906001906020840190610308565b5050565b60018054604080516020601f600260001961010087891615020190951694909404938401819004810282018101909252828152606093909290918301828280156102dd5780601f106102b2576101008083540402835291602001916102dd565b820191906000526020600020905b8154815290600101906020018083116102c057829003601f168201915b5050505050905090565b600080546001600160a01b03191633179055805161024e9060019060208401905b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061034957805160ff1916838001178555610376565b82800160010185558215610376579182015b8281111561037657825182559160200191906001019061035b565b50610382929150610386565b5090565b61023891905b80821115610382576000815560010161038c56fea265627a7a72315820d032d1c3ca36cd567edf28bc7b47a29474a0b8c8686c96809217bfc88a95f08f64736f6c634300050b0032";

    public static final String FUNC_GETBLOCKNUMBER = "getBlockNumber";

    public static final String FUNC_SETGREETING = "setGreeting";

    public static final String FUNC_GREET = "greet";

    public static final String FUNC_CONSTUCTOR = "constuctor";

    protected Greeter(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Greeter(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Greeter load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Greeter(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Greeter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Greeter(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RemoteCall<Greeter> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
                                             BigInteger gasLimit, String message) {
        return deployRemoteCall(Greeter.class, web3j, credentials, gasPrice, gasLimit, BINARY, message );
    }
}
