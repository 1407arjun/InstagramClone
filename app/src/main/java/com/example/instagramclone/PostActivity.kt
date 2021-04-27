package com.example.instagramclone

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        setSupportActionBar(postToolbar)
        postToolbar.setNavigationOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        postToolbar.setOnMenuItemClickListener{menuItem ->
            when (menuItem.itemId){
                R.id.next -> {
                    postUpload()
                }
            }
            return@setOnMenuItemClickListener true
        }

        CropImage.activity().start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            imageUri = result.uri
            currentImageView.setImageURI(imageUri)
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean  {
        /*menuInflater.inflate(R.menu.post_menu, menu)
        return super.onCreateOptionsMenu(menu)*/
    }*/

    private fun postUpload() {
        val progress: ProgressDialog = ProgressDialog(this)
        progress.setMessage("Loading")
        progress.show()

        if (imageUri != null){
            val storageReference: com.google.firebase.storage.StorageReference =
                FirebaseStorage.getInstance().getReference("Posts").child(System.currentTimeMillis().toString() + "." + getFileExtension(imageUri!!))
            val uploadTask: StorageTask<UploadTask.TaskSnapshot> = storageReference.putFile(imageUri!!)
            uploadTask.continueWithTask{task ->
                if (!task.isSuccessful){
                    throw task.exception!!
                }
                return@continueWithTask storageReference.downloadUrl
            }.addOnCompleteListener{task ->
                val downloadUri: Uri? = task.result
                imageUrl = downloadUri.toString()

                var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Posts")
                val postId: String? = reference.push().key

                val map = mutableMapOf<String, String>()
                map["postId"] =  postId!!
                map["imageUrl"] = imageUrl!!

                reference.child(postId).setValue(map)

                /*reference = FirebaseDatabase.getInstance().reference.child("HashTags")
                //var hashTags: List<String> = description.getHashTags()
                if (!hashTags.isEmpty()){
                    for (tag: String in hashTags){
                        map.clear()
                        map["tag"] = tag.toLowerCase()
                        map["postId"] = postId

                        reference.child(tag.toLowerCase()).setValue(map)
                    }*/
                progress.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                }.addOnFailureListener{e ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "No image was selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.contentResolver.getType(uri))
    }

}