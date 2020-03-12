package its.papsi.beritaonlineb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class UtilMessage {
    private Activity activity;
    private AlertDialog progressDialog;

    public UtilMessage(Activity activity) {
        this.activity = activity;

        //inisialisasi progressDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.progress, null);
        builder.setView(view);
        builder.setCancelable(false);

        progressDialog = builder.create();
    }

    public void showProgressBar() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    public void dismissProgressBar() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
