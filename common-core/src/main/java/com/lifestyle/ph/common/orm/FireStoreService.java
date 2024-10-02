package com.lifestyle.ph.common.orm;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FireStoreService {
    private final Firestore db;

    public FireStoreService(Firestore firestore) {
        this.db = firestore;
    }

    public String addDocument(String collection, Map<String, Object> data) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = db.collection(collection).document().set(data);
        return future.get().getUpdateTime().toString();
    }

    public List<Map<String, Object>> getDocuments(String collection) throws ExecutionException, InterruptedException {
        CollectionReference collectionRef = db.collection(collection);
        ApiFuture<QuerySnapshot> future = collectionRef.get();
        return future.get().getDocuments().stream()
                .map(document -> document.getData())
                .collect(Collectors.toList());
    }

    public Map<String, Object> getDocument(String collection, String documentId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(documentId);
        return docRef.get().get().getData();
    }

    public String updateDocument(String collection, String documentId, Map<String, Object> data) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> future = db.collection(collection).document(documentId).set(data);
        return future.get().getUpdateTime().toString();
    }

    public void deleteDocument(String collection, String documentId) throws ExecutionException, InterruptedException {
        db.collection(collection).document(documentId).delete().get();
    }
}
