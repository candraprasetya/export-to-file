package com.kardusinfo.pertemuan11.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kardusinfo.pertemuan11.model.FileModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    private static final String TAG = FileHelper.class.getName();


    public static void writeToFile(Context context, FileModel fileModel) {
        try {
            OutputStreamWriter output = new OutputStreamWriter(context.openFileOutput(fileModel.getFileName(), Context.MODE_PRIVATE));
            output.write(fileModel.getData());
            output.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
    }

    public static FileModel readFromFile(Context context, String fileName) {
        FileModel fileModel = new FileModel();
        try {
            InputStream input = context.openFileInput(fileName);
            if (input != null) {
                InputStreamReader reader = new InputStreamReader(input);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                input.close();
                fileModel.setData(stringBuilder.toString());
                fileModel.setFileName(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
        return fileModel;
    }
}
