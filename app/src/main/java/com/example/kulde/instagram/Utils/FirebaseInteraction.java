package com.example.kulde.instagram.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.kulde.instagram.camera.FilterActivity;
import com.example.kulde.instagram.camera.TakePhotoActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class FirebaseInteraction extends AsyncTask{
    private String userID;
    FirebaseStorage storage;
    StorageReference storageRef;
   // StorageReference imagesRef;
    FirebaseAuth mAuth;

    Bitmap bitmap;
    private double mPhotoUploadProgress = 0;
    Context mContext;
    String caption;



    public FirebaseInteraction(Context context, Bitmap bitmap, String caption) {

        //
        this.bitmap=bitmap;
        //storage = FirebaseStorage.getInstance();

        //imagesRef = storageRef.child("images/name_of_your_image.jpg");
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
        mContext = context;
        this.caption=caption;

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        storageRef = FirebaseStorage.getInstance().getReference();
        uploadImageBitmap(bitmap);
        return null;
    }

    private void uploadImageBitmap(Bitmap bitmap) {
        // upload image via bitmap
        int count = 1;
        bitmap = rotateBitmap(bitmap, -CommResources.rotationdegree);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        assert storageRef != null;
        FilePaths filePaths = new FilePaths();
        StorageReference imagesRef = storageRef.child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + userID + "/photo" + (count + 1));


        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                // add photo to photo node and user photo node



            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                if(progress - 15 > mPhotoUploadProgress){
                    Toast.makeText(mContext, "photo upload progress: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                    mPhotoUploadProgress = progress;
                }

            }
        });
    }

    private Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void addPhototoDatabase(String caption, Bitmap bitmap){

    }



}
