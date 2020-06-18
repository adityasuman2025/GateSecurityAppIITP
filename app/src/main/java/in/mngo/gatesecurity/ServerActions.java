package in.mngo.gatesecurity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerActions  extends AsyncTask<String, Void, Bitmap>
{
//    String base_url = "http://172.16.26.43/key_issue_api/stud_img/";

    String base_url = "http://mngo.in/key_issue_api/stud_img/";

    @Override
    protected Bitmap doInBackground(String... params)
    {
        String type = params[0];
        Bitmap result = null;
        URL url;

        if (type.equals("get_person_photo"))
        {
            try
            {
                String person_roll = params[1];

                String login_url = base_url + person_roll + ".jpg";

                url = new URL(login_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                result = myBitmap;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }
}