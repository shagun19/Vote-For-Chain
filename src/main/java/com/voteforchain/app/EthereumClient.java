package com.voteforchain.app;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

/**
 * Hello world!
 *
 */
public class EthereumClient
{

    public HashMap<String,List<String>> voteForCandidate(List<String> candidates, String votedCandidate, String emailId) throws Exception {
        HashMap<String,List<String>> result = new HashMap<>();
        String credentialsFilePath = System.getProperty("user.dir")+"/UTC--2019-10-08T01-27-41" +
                ".960816000Z--f23c18a0bd1311972b26eb6667c6dd47789dfb35";
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura" +
                ".io/0xF23C18A0BD1311972B26eB6667c6Dd47789DFb35"));
        Credentials credentials = WalletUtils.loadCredentials("Shagun123", credentialsFilePath);
        DynamicArray<Bytes32> candidatesInByte32 = getCandidatesInByte32(candidates);
        Ballot ballot =
                Ballot.deploy(web3j,credentials,ManagedTransaction.GAS_PRICE,Contract.GAS_LIMIT,candidatesInByte32).send();
        ballot.vote(new Utf8String(votedCandidate),new Utf8String(emailId)).send();
        if(ballot.isValid()){
             result.put(ballot.getContractAddress(),Arrays.asList(ballot.getCandidateName().send().getValue(),
                     ballot.getEmaildId().send().getValue(), String.valueOf(ballot.getTimestamp().send().getValue()),
                     ballot.getTransactionReceipt().toString()));
             return result;
        }
        else return null;
    }

    private DynamicArray<Bytes32> getCandidatesInByte32(List<String> candidates){
        List<Bytes32> candidateConverted  = new ArrayList<>();
        for(String candidate: candidates){
            Bytes32 bytes32 =  asciiToHex(candidate);
            candidateConverted.add(bytes32);
        }
        return new DynamicArray<>(candidateConverted);
    }

    public static Bytes32 asciiToHex(String asciiValue)
    {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++)
        {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        String res = hex.toString() + String.join("", Collections.nCopies(32 - (hex.length()/2), "00"));
        Bytes32 bytes32 =  new Bytes32(Numeric.hexStringToByteArray(res));
        return bytes32;
    }
}
