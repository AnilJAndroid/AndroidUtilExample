package com.example.jangid.androidutilsexample;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.jangid.androidutilsexample.Adapter.DataAdapter;
import com.example.jangid.androidutilsexample.Model.ResponseImageUpload;
import com.example.jangid.androidutilsexample.Screens.RecycleviewSample;
import com.example.jangid.androidutilsexample.events.NotificationActions;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationAction;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = MainActivity.this.getClass().getSimpleName();
    String path = null;

    public static final int MAX_RETRIES = 3;
    public static final boolean FIXED_LENGTH_STREAMING_MODE = true;


    @BindView(R.id.btn_file_upload)
    Button btn_file_upload;
    @BindView(R.id.btn_file)
    Button btn_file;
    @BindView(R.id.btn_camera2)
    Button btn_camera2;
    @BindView(R.id.button_alert_dialog)
    Button button_alert_dialog;
    @BindView(R.id.btn_recyleview)
    Button btn_recyleview;
    @BindView(R.id.button_retrofit)
    Button button_retrofit;
    @BindView(R.id.button_toro)
    Button button_toro;
    @BindView(R.id.button_trimvideo)
    Button button_trimvideo;
    @BindView(R.id.button_file_upload)
    Button button_file_upload;
    final int REQUEST_IMAGE_CAPTURE = 1;
    final int REQUEST_GALLERY_CAPTURE = 2;

    @BindView(R.id.button_popup)
    Button button_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn_file_upload.setOnClickListener(this);
        btn_file.setOnClickListener(this);
        btn_camera2.setOnClickListener(this);
        button_alert_dialog.setOnClickListener(this);
        btn_recyleview.setOnClickListener(this);
        button_retrofit.setOnClickListener(this);
        button_toro.setOnClickListener(this);
        button_trimvideo.setOnClickListener(this);
        button_file_upload.setOnClickListener(this);
        button_popup.setOnClickListener(this);
    }

    private void uploadFile(String userid, File file) {
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        APIService service = retrofitHelper.getRetrofit().create(APIService.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        /*MultipartBody.Part body = MultipartBody.Part.createFormData(
                "profile",
                file.getName(),
                requestFile);

        RequestBody user_id = RequestBody.create(MultipartBody.FORM, userid);*/

        RequestBody imagefile = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("profile", file.getName(), requestFile)
                .addFormDataPart("restUserId", userid).build();

        retrofit2.Call<ResponseImageUpload> call = service.UploadProfileImage(imagefile);
        call.enqueue(new Callback<ResponseImageUpload>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseImageUpload> call, Response<ResponseImageUpload> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Some thing went wrongs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseImageUpload> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Some thing went wrongs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btn_file) {
            SelectFile();
        } else if (v == btn_camera2) {
            startActivity(new Intent(getApplicationContext(), Camera2Activity.class));
        } else if (v == btn_file_upload) {
            if (path != null) {
                File file = new File(path);
                uploadFile("15", file);
            } else {
                Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show();
            }
        } else if (v == button_alert_dialog) {
//            Utils.showNoInternetConnection(MainActivity.this);
            Utils.showDialog(MainActivity.this, "Are you sure?", "Accept", "Reject",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "Rejcted", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (v == btn_recyleview) {
            startActivity(new Intent(this, RecycleviewSample.class));
        } else if (v == button_retrofit) {
            callRetrofit();
        } else if (v == button_toro) {
            startActivity(new Intent(this, ToroActivity.class));
        } else if (v == button_trimvideo) {
            startActivity(new Intent(getApplicationContext(), VideoTrimmerActivity.class));
        } else if (v == button_file_upload) {
            try {
                Done("http://pr07002.searchnative.com/api/challenge/company_challenge",
                        "96", "TEST", "test", "04-11-2018", "05-11-2018",
                        "qDgaSnnAwPpt061S1OnTeinE46qNxRMREQjCGXiD", path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if(v == button_popup){
            int[] location = new int[2];
            button_popup.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            Point point = new Point();
            point.x = x;
            point.y = y;
            showSortPopup(MainActivity.this,point,button_popup);
        }
    }

    private void callRetrofit() {
        APIService apiService = new RetrofitHelper().getRetrofit().create(APIService.class);
        Call<String> Res = apiService.groupList(10);
        Res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e(TAG, "onResponse: " + response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void showSortPopup(final Context context, Point p,Button btn)
    {
        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.custom_popup, null);

        // Creating the PopupWindow
        PopupWindow changeSortPopUp = new PopupWindow(context);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = -100;
        int OFFSET_Y = 0;

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);


    }

    private void SelectFile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Select File!");
        String[] options = new String[]{"Camera", "Gallery", "Cancel"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selectCamera();
                        break;
                    case 1:
                        showGallery();
                        break;
                    case 2:
                        break;
                }
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void selectCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        return image;

    }


    void Done(String serverUrl, String u_id, String title, String description,
              String start_date, String end_date, String APIKEY, String filepath) throws MalformedURLException, FileNotFoundException {
        final String uploadId = UUID.randomUUID().toString();

        final MultipartUploadRequest request = new MultipartUploadRequest(this, uploadId, serverUrl)
                        .setMethod("POST")
                        .setUtf8Charset()
                        .setNotificationConfig(getNotificationConfig(uploadId, R.string.multipart_upload))
                        .setMaxRetries(MAX_RETRIES);
                        //.setCustomUserAgent(getUserAgent())
//                        .setUsesFixedLengthStreamingMode(FIXED_LENGTH_STREAMING_MODE);
        request.addParameter("u_id", u_id);
        request.addParameter("title", title);
        request.addParameter("description", description);
        request.addParameter("start_date", start_date);
        request.addParameter("end_date", end_date);
        request.addParameter("APIKEY", APIKEY);
        request.addFileToUpload(filepath, "video");
        request.startUpload();
    }


    UploadNotificationConfig getNotificationConfig(final String uploadId, @StringRes int title) {
        UploadNotificationConfig config = new UploadNotificationConfig();

        PendingIntent clickIntent = PendingIntent.getActivity(
                this, 1, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        config.setTitleForAllStatuses(getString(title))
                .setRingToneEnabled(true)
                .setClickIntentForAllStatuses(clickIntent)
                .setClearOnActionForAllStatuses(true);


        config.getProgress().message = getString(R.string.uploading);
        config.getProgress().iconResourceID = android.R.drawable.ic_menu_upload;
        config.getProgress().iconColorResourceID = Color.BLUE;
        config.getProgress().actions.add(new UploadNotificationAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                getString(R.string.cancel_upload),
                NotificationActions.getCancelUploadAction(this, 1, uploadId)));

        config.getCompleted().message = getString(R.string.upload_success);
        config.getCompleted().iconResourceID = android.R.drawable.stat_sys_upload_done;
        config.getCompleted().iconColorResourceID = Color.GREEN;

        config.getError().message = getString(R.string.upload_error);
        config.getError().iconResourceID = android.R.drawable.stat_notify_error;
        config.getError().iconColorResourceID = Color.RED;

        config.getCancelled().message = getString(R.string.upload_cancelled);
        config.getCancelled().iconResourceID = android.R.drawable.ic_menu_close_clear_cancel;
        config.getCancelled().iconColorResourceID = Color.YELLOW;

        return config;
    }


    private void showGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), REQUEST_GALLERY_CAPTURE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY_CAPTURE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    // Get the path
                    try {
//                        path = Comman.getPath(this, uri);
                        path = FileSelector.getFilePath(getApplicationContext(), uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + path);
                }
                break;
            case REQUEST_IMAGE_CAPTURE:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
