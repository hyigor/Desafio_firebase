package br.com.digitalhouse.desafio4

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Repositorio {
    val coluna = "Jogos"
    val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    val collection:CollectionReference = database.collection(coluna)
    val storage = FirebaseStorage.getInstance().getReference(collection.document().id)
}