package com.voteforchain.app;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, CipherException, Exception {
        Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura" +
                ".io/0xF23C18A0BD1311972B26eB6667c6Dd47789DFb35"));
        Credentials credentials = WalletUtils.loadCredentials("Shagun123", "/Users/shagunbhatia/Vote-For-Chain" +
                "/voteforchain/test-net-blockchain/keystore/UTC--2019-10-08T01-27-41.960816000Z--f23c18a0bd1311972b26eb6667c6dd47789dfb35");
        /*TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0xDc1Fb60F7E1eF6ef052A014EFbDF82debd803D06", BigDecimal.valueOf(100), Convert.Unit.ETHER).sendAsync
                ().get();
         */
     //   System.out.println("Transaction complete : " + transferReceipt.getTransactionHash());
        Greeter contract =
                Greeter.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT,"abcd").send();
        String contractAddress = contract.getContractAddress();
        System.out.println(contract.isValid());
        System.out.println(contractAddress);

        System.exit(0);
    }
}
