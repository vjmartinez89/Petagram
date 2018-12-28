package com.vjmartinez.petagram.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class SendEmailAsyncTask extends AsyncTask {

    Mail m = new Mail("vijumaba89@gmail.com", "V1jum4b489#");
    Context context = null;

    public SendEmailAsyncTask(Context context, String from, String[] to, String subject, String body) {
        m.setTo(to);
        m.setFrom(from);
        m.setSubject(subject);
        m.setBody(body);
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            m.send();
            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), m.getTo() + "failed");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}