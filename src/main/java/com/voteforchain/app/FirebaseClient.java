package com.voteforchain.app;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.web3j.tx.exceptions.ContractCallException;

import java.io.FileInputStream;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;



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

    public static void main(String[] args) throws Exception {
        String filePathPrivateKey = System.getProperty("user.dir")+"/voteforchain-5e81b-firebase-adminsdk-972jz" +
                "-1caa60a8c4.json";
        FileInputStream refreshToken = new FileInputStream(filePathPrivateKey);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setProjectId("voteforchain-5e81b")
                .build();
        FirebaseApp.initializeApp(options);
        HashMap<Integer,List<String>> databaseDocument = registerVoteOnDatabase();
        int documentId = databaseDocument.keySet().iterator().next();
        String emailId = databaseDocument.get(documentId).get(0);
        String candidate = databaseDocument.get(documentId).get(1);
        if(documentId!=-1){
            System.out.println("Vote registered for "+emailId+" on database with id: "+documentId);
            EthereumClient ethereumClient = new EthereumClient();
            List<String> candidates = Arrays.asList("Candidate1","Candidate2");
            HashMap<String,List<String>> contract = ethereumClient.voteForCandidate(candidates,candidate,emailId);
            if(contract!=null){
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
        System.exit(0);
    }

}
