package com.paraxco.imagetools.ImagePickerHelpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.paraxco.commontools.Activities.BaseActivity;
import com.paraxco.commontools.Utils.Permision.PermisionUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Amin on 6/10/2017.
 */

public class ImagePickerHelper {
    private static final int RESULT_LOAD_IMAGE = 12;
    private static final int PIC_CROP = 34;
    private static final int PICK_IMAGE_ID = 88;
    BaseActivity baseActivity;
    private File picture;
    private Uri cropingURI;

    public ImagePickerHelper(BaseActivity activity) {
        this.baseActivity = activity;
    }

    public void getImage(final imageListener imageListener) {
        baseActivity.requestPermisions(new String[]{"android.permission.CAMERA","android.permission.WRITE_EXTERNAL_STORAGE"}, new PermisionUtils.PermisionGrantListener() {
            @Override
            public void onPermisionGranted() {
                doGetImageWorks(imageListener);
            }

            @Override
            public void onPermisionDenied() {

            }
        });


    }

    private void doGetImageWorks(final imageListener imageListener) {
        if (baseActivity != null) {
            baseActivity.setOnActivityResultListener(new BaseActivity.OnActivityResultListener() {
                @Override
                public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//
                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                        Cursor cursor = baseActivity.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String picturePath = cursor.getString(columnIndex);
//                        cursor.close();
//                        imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
////                        String picturePath2 = ImageUtils.getPathFromURI(baseActivity, data.getData());
//                        ImageUtils.loadCircleWithGlide(baseActivity,picture.getAbsolutePath(), imageView);
////                        parentImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        performCrop(selectedImage);

                    } else if (requestCode == PIC_CROP && resultCode == RESULT_OK) {
                        deleteCropingURI();
                        Bundle extras = data.getExtras();
                        // get the cropped bitmap
                        Bitmap selectedBitmap = extras.getParcelable("data");
                        if (imageListener != null) {
                            try {
                                imageListener.onImageReady(getBitmapFile(selectedBitmap), selectedBitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    } else if (requestCode == PICK_IMAGE_ID && resultCode == RESULT_OK) {
//                        Uri selectedImage = data.getData();

                        Bitmap bitmap = ImagePicker.getImageFromResult(baseActivity, resultCode, data);
                        performCrop(getImageUri(baseActivity, bitmap));

//                        if (imageListener != null) {
//                            try {
//                                imageListener.onImageReady(getBitmapFile(bitmap),bitmap);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }
//                    ImageUtils.loadCircleWithGlide(baseActivity, picture.getAbsolutePath(), imageView);

                }
            });
//            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            i.putExtra("crop", "true");
//            i.putExtra("aspectX", 1);
//            i.putExtra("aspectY", 1);
//            i.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
//
//            baseActivity.startActivityForResult(i, RESULT_LOAD_IMAGE);


//            dispatchTakePictureIntent();
            Intent chooseImageIntent = ImagePicker.getPickImageIntent(baseActivity,"");
            baseActivity.startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);

        }
    }

    private Uri getTempUri() {


        File storageDir = baseActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
////
        File imagesFolder = new File(storageDir, "PIC");
        imagesFolder.mkdirs();

        picture = new File(imagesFolder, "tempImage.jpg");
        try {
            picture.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        File storageDir = baseActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        picture = null;
//        try {
//            picture = File.createTempFile(
//                    "tempImage.jpg",  /* prefix */
//                    ".jpg",         /* suffix */
//                    storageDir      /* directory */
//            );
//            picture.createNewFile();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Save a file: path for use with ACTION_VIEW intents
        String path = picture.getAbsolutePath();

        if (picture != null) {
            Uri photoURI = FileProvider.getUriForFile(baseActivity,
                    "com.example.basictools.imagetools",
                    picture);
            return photoURI;

        }

        return null;
//        File storageDir = baseActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
////
//        File imagesFolder = new File(storageDir, "varasPIC");
//        imagesFolder.mkdirs();
//
//        picture = new File(imagesFolder, "tempImage.jpg");
//        try {
//            picture.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Uri photoURI = FileProvider.getUriForFile(baseActivity,
//                "com.paraxco.varasservice.android.fileprovider",
//                image2);
//        return photoURI2;
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = baseActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 14;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(baseActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
//            File photoFile = null;
            try {
                picture = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (picture != null) {
                Uri photoURI = FileProvider.getUriForFile(baseActivity,
                        "com.example.android.fileprovider",
                        picture);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                takePictureIntent.putExtra("crop", "true");
//                takePictureIntent.putExtra("aspectX", 1);
//                takePictureIntent.putExtra("aspectY", 1);
                takePictureIntent.putExtra("return-data", true);

                baseActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void deleteCropingURI() {
        new File(cropingURI.getPath()).delete();

    }

    private void performCrop(Uri selectedImage) {

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        // indicate image type and Uri
        cropingURI = selectedImage;
        cropIntent.setDataAndType(selectedImage, "image/*");
        // set crop properties here
        cropIntent.putExtra("crop", true);
        // indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // indicate output X and Y
        cropIntent.putExtra("outputX", 400);
        cropIntent.putExtra("outputY", 400);
        // retrieve data on return
        cropIntent.putExtra("return-data", true);

//        Uri photoURI = FileProvider.getUriForFile(baseActivity,
//                "com.example.android.fileprovider",
//                picture);
//        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        // start the activity - we handle returning in onActivityResult
        baseActivity.startActivityForResult(cropIntent, PIC_CROP);
        // respond to users whose devices do not support the crop action
    }

    private File getBitmapFile(Bitmap bitmap) throws IOException {
        //create a file to write bitmap data
        File file = new File(baseActivity.getCacheDir(), "temp.jpeg");
        if (file.exists())
            file.delete();
        file.createNewFile();

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return file;
    }

    public interface imageListener {
        void onImageReady(File file, Bitmap bitmap);
    }
}
