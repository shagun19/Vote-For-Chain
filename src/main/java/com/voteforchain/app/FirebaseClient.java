package com.voteforchain.app;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import jnr.ffi.annotations.In;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class FirebaseClient {

    private static HashMap<String,Object> dataMap(){
        HashMap<String, Object> docData = new HashMap<>();
        docData.put("emailId", "f.edu");
        docData.put("blockHash", "CA");
        docData.put("timestamp", Instant.now().getEpochSecond());
        docData.put("candidate", "Group1");
        return docData;
    }

    private static int addRecordToDatabase() throws ExecutionException, InterruptedException {
        HashMap<String, Object> docData = dataMap();
        String hashCodeEmailId = String.valueOf(docData.get("emailId").toString().hashCode());
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> future =
                db.collection("VotesRegistered").document(hashCodeEmailId).get();
        if(future.get().getData()==null){
            db.collection("VotesRegistered").document(hashCodeEmailId).set(docData);
            return Integer.parseInt(hashCodeEmailId);
        }
        else return -1;
    }
    public static void main(String[] args) throws Exception {
        FileInputStream refreshToken = new FileInputStream("/Users/shagunbhatia/VoteForChain/voteforchain-5e81b-firebase-adminsdk-972jz-1caa60a8c4.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setProjectId("voteforchain-5e81b")
                .build();
        FirebaseApp.initializeApp(options);
        int recordIndex = addRecordToDatabase();
        EthereumClient ethereumClient = new EthereumClient();
        if(recordIndex!=-1){
            System.out.println(recordIndex);
            List<String> candidates = Arrays.asList("Group1","Group2");
            List<String> contractAddress = ethereumClient.voteForCandidate(candidates,"Group1","f.edu");
            if(contractAddress!=null){
                System.out.println("Contract submitted successfully "+contractAddress.get(0));
                System.out.println(contractAddress);
                System.exit(0);
            }
        }
    }

}
