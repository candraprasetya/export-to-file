package com.kardusinfo.pertemuan11;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kardusinfo.pertemuan11.helper.FileHelper;
import com.kardusinfo.pertemuan11.model.FileModel;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    //Deklrasi
    EditText editTitle, editContent;
    Button buttonNew, buttonOpen, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INisialisasi
        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);
        buttonNew = findViewById(R.id.button_new);
        buttonOpen = findViewById(R.id.button_open);
        buttonSave = findViewById(R.id.button_save);


        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFile();
            }
        });

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
    }


    private void newFile() {
        editTitle.setText("Untitled");
        editContent.setText("");
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        editTitle.setText(fileModel.getFileName());
        editContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFileName() + " data", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getFilesDir().list());
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadData(items[which].toString());
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void saveFile() {
        //Check Title
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi", Toast.LENGTH_SHORT).show();
        }
        //Check Data
        else if (editContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Konten harus diisi", Toast.LENGTH_SHORT).show();
        } else {
            //Mmebuat Variable
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();

            //Panggil FileModel
            FileModel fileModel = new FileModel();

            fileModel.setFileName(title);
            fileModel.setData(content);

            //Panggil fungsi FIleHelper.writeToFile
            FileHelper.writeToFile(this, fileModel);

            Toast.makeText(this, fileModel.getFileName() + " file saved", Toast.LENGTH_SHORT).show();
        }
    }

}