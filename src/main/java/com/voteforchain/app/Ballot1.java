package com.voteforchain.app;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class Ballot1 extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516106d93803806106d98339818101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b82525081516020918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040525050600480546001600160a01b03191633179055505080516100e69060009060208401906100ed565b5050610155565b828054828255906000526020600020908101928215610128579160200282015b8281111561012857825182559160200191906001019061010d565b50610134929150610138565b5090565b61015291905b80821115610134576000815560010161013e565b90565b610575806101646000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063188ec3561461005157806367f328bd1461006b57806384e6c0a7146100e8578063e8d5940d146100f0575b600080fd5b61005961021f565b60408051918252519081900360200190f35b610073610226565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100ad578181015183820152602001610095565b50505050905090810190601f1680156100da5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100736102bd565b61021d6004803603604081101561010657600080fd5b81019060208101813564010000000081111561012157600080fd5b82018360208201111561013357600080fd5b8035906020019184600183028401116401000000008311171561015557600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156101a857600080fd5b8201836020820111156101ba57600080fd5b803590602001918460018302840111640100000000831117156101dc57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061031d945050505050565b005b6002545b90565b60038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156102b25780601f10610287576101008083540402835291602001916102b2565b820191906000526020600020905b81548152906001019060200180831161029557829003601f168201915b509394505050505090565b60018054604080516020601f600260001961010087891615020190951694909404938401819004810282018101909252828152606093909290918301828280156102b25780601f10610287576101008083540402835291602001916102b2565b6004546001600160a01b0316331461033457600080fd5b61038e600080548060200260200160405190810160405280929190818152602001828054801561038357602002820191906000526020600020905b81548152602001906001019080831161036f575b5050505050836103f9565b6103c95760405162461bcd60e51b81526004018080602001828103825260388152602001806105096038913960400191505060405180910390fd5b81516103dc906001906020850190610470565b504260025580516103f4906003906020840190610470565b505050565b6000806104058361044e565b905060005b8451811015610441578185828151811061042057fe5b6020026020010151141561043957600192505050610448565b60010161040a565b5060009150505b92915050565b8051600090829061046357506000905061046b565b505060208101515b919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104b157805160ff19168380011785556104de565b828001600101855582156104de579182015b828111156104de5782518255916020019190600101906104c3565b506104ea9291506104ee565b5090565b61022391905b808211156104ea57600081556001016104f456fe566f7465642063616e6469646174652073686f756c6420626520616d6f6e67206c697374206f662076616c69642063616e64696461746573a265627a7a7231582027acba6f520d079c58b40d21c6a7d43b258d030e4193355b33af254ecd2b2bf164736f6c634300050b0032";

    public static final String FUNC_GETTIMESTAMP = "getTimestamp";

    public static final String FUNC_GETEMAILDID = "getEmaildId";

    public static final String FUNC_GETCANDIDATENAME = "getCandidateName";

    public static final String FUNC_VOTE = "vote";

    @Deprecated
    protected Ballot1(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }



    @Deprecated
    protected Ballot1(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }



    public RemoteCall<Uint256> getTimestamp() {
        final Function function = new Function(FUNC_GETTIMESTAMP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getEmaildId() {
        final Function function = new Function(FUNC_GETEMAILDID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<Utf8String> getCandidateName() {
        final Function function = new Function(FUNC_GETCANDIDATENAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> vote(Utf8String proposal, Utf8String _emailId) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(proposal, _emailId), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Ballot1 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot1(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Ballot1 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot1(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RemoteCall<Ballot1> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, DynamicArray<Bytes32> proposalNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(proposalNames));
        return deployRemoteCall(Ballot1.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Ballot1> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, DynamicArray<Bytes32> proposalNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(proposalNames));
        return deployRemoteCall(Ballot1.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}