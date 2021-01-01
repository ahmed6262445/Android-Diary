package com.example.diary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.diary.Models.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostFragment extends Fragment {
    long maxId;

    Button btnAddImage;
    Button btnRemoveImage;
    Button btnAddPost;
    Button btnBack;

    TextInputLayout tilPost;

    RadioGroup rgEmotions;

    RadioButton rbLove;
    RadioButton rbExcited;
    RadioButton rbHappy;
    RadioButton rbNaughty;
    RadioButton rbContent;
    RadioButton rbSad;
    RadioButton rbDisappointed;
    RadioButton rbAngry;

    Uri selectedImageUri;

    DatePicker datePicker;

    ImageView imageView;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
//    FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.storageReference = firebaseStorage.getReference();
//        this.firebaseDatabase = FirebaseDatabase.getInstance();

        this.btnAddImage = (Button) view.findViewById(R.id.btn_add_image);
        this.btnRemoveImage = (Button) view.findViewById(R.id.btn_remove_image);
        this.btnAddPost = (Button) view.findViewById(R.id.btn_add_post);
        this.btnBack = (Button) view.findViewById(R.id.btn_back);

        this.tilPost = (TextInputLayout) view.findViewById(R.id.til_post);

        this.datePicker = (DatePicker) view.findViewById(R.id.date_picker);

        this.rgEmotions = (RadioGroup) view.findViewById(R.id.rg_emotions);

        this.rbLove = (RadioButton) view.findViewById(R.id.rb_love);
        this.rbNaughty = (RadioButton) view.findViewById(R.id.rb_naughty);
        this.rbHappy = (RadioButton) view.findViewById(R.id.rb_happy);
        this.rbContent = (RadioButton) view.findViewById(R.id.rb_content);
        this.rbDisappointed = (RadioButton) view.findViewById(R.id.rb_disapointed);
        this.rbAngry = (RadioButton) view.findViewById(R.id.rb_angry);
        this.rbExcited = (RadioButton) view.findViewById(R.id.rb_excited);
        this.rbSad = (RadioButton) view.findViewById(R.id.rb_sad);

        this.imageView = (ImageView) view.findViewById(R.id.imageView);

        this.btnAddImage.setOnClickListener(openImageDialog);
        this.btnRemoveImage.setOnClickListener(removeImage);
        this.btnAddPost.setOnClickListener(addPost);
        this.btnBack.setOnClickListener(goToHomeView);
    }

    // Click Listeners
    View.OnClickListener openImageDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    this.selectedImageUri = imageReturnedIntent.getData();
                    this.imageView.setImageURI(selectedImageUri);
                    this.imageView.setVisibility(View.VISIBLE);
                    this.btnAddImage.setVisibility(View.GONE);
                    this.btnRemoveImage.setVisibility(View.VISIBLE);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    this.selectedImageUri = imageReturnedIntent.getData();
                    this.imageView.setImageURI(selectedImageUri);
                    this.imageView.setVisibility(View.VISIBLE);
                    this.imageView.setVisibility(View.VISIBLE);
                    this.btnAddImage.setVisibility(View.GONE);
                    this.btnRemoveImage.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    View.OnClickListener removeImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            imageView.setImageDrawable(null);
            imageView.setVisibility(View.GONE);
            btnRemoveImage.setVisibility(View.GONE);
            btnAddImage.setVisibility(View.VISIBLE);
            selectedImageUri = null;
        }
    };

    View.OnClickListener goToHomeView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
        }
    };

    View.OnClickListener addPost = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Post post = new Post();
            post.setPost(tilPost.getEditText().getText().toString());
            post.setDay(datePicker.getDayOfMonth());
            post.setMonth(datePicker.getMonth());
            post.setYear(datePicker.getYear());

            if (rbAngry.isChecked()) {
                post.setEmotion("Feeling Angry!");
            }
            else if (rbContent.isChecked()) {
                post.setEmotion("Feeling Content");
            }
            else if (rbDisappointed.isChecked()) {
                post.setEmotion("Feeling disappointed");
            }
            else if (rbExcited.isChecked()) {
                post.setEmotion("Feeling Excited");
            }
            else if (rbHappy.isChecked()) {
                post.setEmotion("Feeling Happy");
            }
            else if (rbLove.isChecked()) {
                post.setEmotion("Feeling loved");
            }
            else if (rbNaughty.isChecked()) {
                post.setEmotion("Feeling Naughty");
            }
            else if (rbSad.isChecked()) {
                post.setEmotion("Feeling Sad");
            }

            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (selectedImageUri != null) {
                post.setPhoto("images/" + firebaseUser.getUid().toString()
                        + "/" + String.valueOf(maxId+1)
                        + "/" + new File(String.valueOf(selectedImageUri)).getName().toString());
            }
            else {
                post.setPhoto(null);
            }

            DocumentReference documentReference = firestore.collection("users")
                    .document(firebaseUser.getUid().toString()).collection("posts").document();
            String postId = documentReference.getId();

            documentReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext().getApplicationContext(), "Post Added Successfully",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSucess: Post created for " + String.valueOf(maxId+1));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext().getApplicationContext(), "Unable to add post at the moment\n" +
                                    "Please try later",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFail: Unable to add post");
                }
            });

            if (selectedImageUri != null) {
//                ProgressBar progressBar = new ProgressBar(getContext());

                StorageReference ref = storageReference.child("images/" + firebaseUser.getUid().toString()
                        + "/" + postId.toString()
                        + "/" + new File(String.valueOf(selectedImageUri)).getName().toString());

                ref.putFile(selectedImageUri)
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot) {

                                        // Image uploaded successfully
                                        // Dismiss dialog
//                                        progressDialog.dismiss();
                                        Toast
                                                .makeText(getActivity().getApplicationContext(),
                                                        "Image Uploaded!!",
                                                        Toast.LENGTH_LONG)
                                                .show();
                                    }
                                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Error, Image not uploaded
//                                progressDialog.dismiss();
                                Toast
                                        .makeText(getActivity().getApplicationContext(),
                                                "Failed to upload image" + e.getMessage(),
                                                Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
//                        .addOnProgressListener(
//                                new OnProgressListener<UploadTask.TaskSnapshot>() {
//
//                                    // Progress Listener for loading
//                                    // percentage on the dialog box
//                                    @Override
//                                    public void onProgress(
//                                            UploadTask.TaskSnapshot taskSnapshot) {
//                                        double progress
//                                                = (100.0
//                                                * taskSnapshot.getBytesTransferred()
//                                                / taskSnapshot.getTotalByteCount());
//                                        progressDialog.setMessage(
//                                                "Uploaded "
//                                                        + (int) progress + "%");
//                                    }
//                                }

            }
        }
    };
}