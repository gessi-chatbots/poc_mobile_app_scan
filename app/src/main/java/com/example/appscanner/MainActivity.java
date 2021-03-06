package com.example.appscanner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class MainActivity extends AppCompatActivity {

    private static final String[] PERMISSIONS =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void scanApps(View view) {
        verifyPermissions();
        String[] idList = getInstalledAppsPackageNames();
        printAsJSONtoFile(idList);
    }

    private String[] getInstalledAppsPackageNames() {
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> listInstalledApps = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        String[] list = new String[listInstalledApps.size()];
        int i = 0;
        for (ApplicationInfo appInfo : listInstalledApps) {
            list[i] = appInfo.packageName;
            ++i;
        }
        return list;
    }

    private void verifyPermissions() {
        int permissionStorage = ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS,1);
        }
    }

    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void printAsJSONtoFile(String[] array) {
        try {
            File data = new File(getDataDir(),"installedAppsPackageNames.json");
            data.createNewFile();
            Writer writer = new FileWriter(data);
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.beginArray();
            for (String packageName : array) {
                jsonWriter.value(packageName);
            }
            jsonWriter.endArray();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            displayExceptionMessage(e.getMessage());
        }
    }

    public JSONArray generateJSONArray(String[] idList) throws JSONException {
        return new JSONArray(idList);
    }


}