package at.tugraz.ist.swe.lang;


import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
public class ImportActivity extends AppCompatActivity {
    private int request_code =1, FILE_SELECT_CODE =101;

    private String TAG ="mainactivty";
    public String  actualfilepath="";
    Vocabulary vocabulary;
    TextView filePath;
    Button goBack;
    Button selectFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        filePath =(TextView) findViewById(R.id.filePath);
        goBack =(Button) findViewById(R.id.goToMain);
        selectFile = (Button) findViewById(R.id.selectFile);

        vocabulary = new Vocabulary(getApplicationContext());
        vocabulary.init();

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddWords();
            }
        });

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // start runtime permission
            Boolean hasPermission =( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission){
                Toast.makeText(getBaseContext(), "Please grant the permissions", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
            }else {
                showFileChooser();
            }
        }else {
            showFileChooser();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                    showFileChooser();
                }else {

                }
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(  Intent.createChooser(intent, "Select a File to Upload"),  FILE_SELECT_CODE);
        } catch (Exception e) {
            Log.e(TAG, " choose file error "+e.toString());
        }
    }


    // Source: https://www.blueappsoftware.com/how-to-read-text-file-in-android-tutorial/ hereafter



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_CODE){
            if (resultCode == RESULT_OK){
                try {
                    Uri fileUri = data.getData();
                    //InputStream stream = null;
                    String tempID= "", id ="";
                    Uri uri = data.getData();

                    if (fileUri.getAuthority().equals("media")){
                        tempID =   fileUri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/")+1);
                        id = tempID;
                        Uri contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        String selector = MediaStore.Images.Media._ID+"=?";
                        actualfilepath = getFileData( contenturi, selector, new String[]{id}  );
                    }else if (fileUri.getAuthority().equals("com.android.providers.media.documents")){
                        tempID = DocumentsContract.getDocumentId(fileUri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];
                        Uri contenturi = null;
                        String selector = "_id=?";
                        actualfilepath = getFileData( contenturi, selector, new String[]{id}  );
                    } else if (fileUri.getAuthority().equals("com.android.providers.downloads.documents")){
                        tempID =   fileUri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/")+1);
                        id = tempID;
                        Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                        actualfilepath = getFileData( contenturi, null, null  );
                    }else if (fileUri.getAuthority().equals("com.android.externalstorage.documents")){
                        tempID = DocumentsContract.getDocumentId(fileUri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];

                        if (type.equals("primary")){
                            actualfilepath=  Environment.getExternalStorageDirectory()+"/"+id;
                        }
                    }
                    File fileToImport = new File(actualfilepath);

                    String tempPath =  uri.getPath();
                    if (tempPath.contains("//")){
                        tempPath = tempPath.substring(tempPath.indexOf("//")+1);
                    }
                    if ( actualfilepath.equals("") || actualfilepath.equals(" ")) {
                        fileToImport = new File(tempPath);
                    }



                    vocabulary.importVocabularyTFromFile(fileToImport);
                    vocabulary.storeFile();

                    filePath.setText("File successfully Imported");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public String getFileData( Uri uri, String selection, String[] selectarg){
        String filepath ="";
        Cursor cursor = null;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor =  getContentResolver().query( uri, projection, selection, selectarg, null);
        if (cursor!= null){
            cursor.moveToFirst();
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
        }
        if (cursor!= null)
            cursor.close();
        return  filepath;
    }

    public void openAddWords() {
        Intent intent = new Intent(this, AddWordsActivity.class);
        startActivity(intent);
    }
}