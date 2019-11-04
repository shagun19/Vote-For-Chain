package com.voteforchain.app;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.web3j.tx.exceptions.ContractCallException;

import java.io.FileInputStream;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

enum OPERATIONS {REGISTER_VOTE_DB, REGISTER_VOTE_BLOCKCHAIN, GET_TOTAL_VOTES, VERIFY_BLOCK};

public class FirebaseClient {

    private static HashMap<String,Object> dataMap(){
        HashMap<String, Object> docData = new HashMap<>();
        String emailId = RandomStringUtils.random(6, true, false)+"@ini.edu";
        docData.put("emailId", emailId.toLowerCase());
        docData.put("blockHash", "");
        docData.put("timestamp", Instant.now().getEpochSecond());
        docData.put("candidate", "Candidate1");
        return docData;
    }

    private static HashMap<Integer,List<String>> registerVoteOnDatabase() throws ExecutionException, InterruptedException {
        HashMap<String, Object> docData = dataMap();
        HashMap<Integer,List<String>> databaseDocument = new HashMap<>();
        boolean alreadyVoted = checkAlreadyVoted(docData.get("emailId").toString());
        if (!alreadyVoted) {
            String hashCodeEmailId = String.valueOf(docData.get("emailId").toString().hashCode());
            Firestore db = FirestoreClient.getFirestore();
            db.collection("VotesRegistered").document(hashCodeEmailId).set(docData);
            databaseDocument.put(Integer.parseInt(hashCodeEmailId),Arrays.asList(docData.get("emailId").toString(),
                    docData.get("candidate").toString()));
        }
        return databaseDocument;
    }

    private static boolean checkAlreadyVoted(String emailId) throws ExecutionException, InterruptedException {
        String hashCodeEmailId = String.valueOf(emailId.hashCode());
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> future =
                db.collection("VotesRegistered").document(hashCodeEmailId).get();
        if(future.get().getData()!=null) return true;
        else return false;
    }

    private static HashMap<String,List<String>> registerVoteOnBlockChain() throws Exception {
        HashMap<Integer,List<String>> databaseDocument = registerVoteOnDatabase();
        HashMap<String,List<String>> contract= new HashMap<>();
        int documentId = databaseDocument.keySet().iterator().next();
        String emailId = databaseDocument.get(documentId).get(0);
        String candidate = databaseDocument.get(documentId).get(1);
        if(documentId!=-1){
            System.out.println("Vote registered for: "+emailId+" on database with id: "+documentId);
            EthereumClient ethereumClient = new EthereumClient();
            List<String> candidates = Arrays.asList("Candidate1","Candidate2");
            contract = ethereumClient.voteForCandidate(candidates,candidate,emailId);
            if(!contract.isEmpty()){
                System.out.println("Contract deployed with address: "+contract.keySet().iterator().next());
                System.out.println("Contract details: "+contract.get(contract.keySet().iterator().next()));
            }
            else{
                throw new ContractCallException("Contract could not be created");
            }
        }
        else{
            System.out.println(emailId+" "+"has already been registered as a vote for candidate "+candidate);
        }
        return contract;
    }

    private static void updateBlockHash(int documentIdHashCode, String blockHash) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("VotesRegistered").document(String.valueOf(documentIdHashCode)).update("blockHash",blockHash);
    }

    private static HashMap<String, Integer> getTotalVotes(List<String> candidates) throws ExecutionException,
            InterruptedException {
        HashMap<String, Integer> totalVotes = new HashMap<>();
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference votes = db.collection("VotesRegistered");
        for(String candidate: candidates){
            Query query = votes.whereEqualTo("candidate",candidate);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            totalVotes.put(candidate,querySnapshot.get().getDocuments().size());

        }
        return totalVotes;
    }

    public static void main(String[] args) throws Exception {
        String filePathPrivateKey = System.getProperty("user.dir")+"/voteforchain-5e81b-firebase-adminsdk-972jz" +
                "-1caa60a8c4.json";
        FileInputStream refreshToken = new FileInputStream(filePathPrivateKey);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setProjectId("voteforchain-5e81b")
                .build();
        FirebaseApp.initializeApp(options);
        if(OPERATIONS.valueOf(args[0]).equals(OPERATIONS.REGISTER_VOTE_DB)){
           HashMap<Integer, List<String>> document = registerVoteOnDatabase();
           int documentId = document.keySet().iterator().next();
            if(documentId!=-1){
                System.out.println("Vote transaction Id: "+documentId);
                System.out.println("Vote has been registered for: "+ document.get(documentId).get(0)+" for candidate: "+
                        document.get(documentId).get(1));
            }
            else{
                throw new Exception("Vote could not be registered");
            }
        }
        else if(OPERATIONS.valueOf(args[0]).equals(OPERATIONS.REGISTER_VOTE_BLOCKCHAIN)){
            HashMap<String,List<String>> contract = registerVoteOnBlockChain();
            System.out.println("Updating block hash value in the database");
            String blockAddress = contract.keySet().iterator().next();
            updateBlockHash(contract.get(blockAddress).get(1).hashCode(), blockAddress);
            Firestore db = FirestoreClient.getFirestore();
            Thread.sleep(10000);
            ApiFuture<DocumentSnapshot> future =
                    db.collection("VotesRegistered").document(String.valueOf(contract.get(blockAddress).get(1).hashCode())).get();
            System.out.println("Block Hash updated: "+future.get().getData());
        }
        else if(OPERATIONS.valueOf(args[0]).equals(OPERATIONS.VERIFY_BLOCK)){
            String emailId = args[1];
            String hashCodeEmailId = String.valueOf(emailId.hashCode());
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentSnapshot> future =
                    db.collection("VotesRegistered").document(hashCodeEmailId).get();
            String contractAddress = future.get().getData().get("blockHash").toString();
            EthereumClient ethereumClient = new EthereumClient();
            HashMap<String,List<String>> obtainedContract = ethereumClient.verifyVotedContract(contractAddress);
            if(obtainedContract!=null){
                System.out.println("Contract Address: "+contractAddress);
                System.out.println("Contract details for emailId: "+emailId+" "+obtainedContract.get(contractAddress));
            }
            else{
                throw new ContractCallException("Contract could not be obtained");
            }
        }
        else if(OPERATIONS.valueOf(args[0]).equals(OPERATIONS.GET_TOTAL_VOTES)){
            List<String> candidates = Arrays.asList("Candidate1","Candidate2");
            HashMap<String, Integer> totalVote = getTotalVotes(candidates);
            for (String candidate : totalVote.keySet()) {
                System.out.println("Candidate: "+candidate+", "+"Total votes: "+totalVote.get(candidate));
            }
        }
        else {
            throw new Exception("Choose among REGISTER_VOTE_DB, REGISTER_VOTE_BLOCKCHAIN, " +
                   "GET_TOTAL_VOTES, VERIFY_BLOCK <emailId> ");
        }
        System.exit(0);
    }
}
